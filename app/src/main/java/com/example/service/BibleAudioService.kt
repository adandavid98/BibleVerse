package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.drawable.Icon
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.example.MainActivity
import com.example.data.bible.BibleCatalog
import com.example.data.bible.OfflineBibleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class BibleAudioService : Service(), TextToSpeech.OnInitListener {

    companion object {
        const val CHANNEL_ID = "bible_audio_playback_channel"
        const val NOTIFICATION_ID = 2002

        const val ACTION_START = "com.example.action.AUDIO_START"
        const val ACTION_PLAY = "com.example.action.AUDIO_PLAY"
        const val ACTION_PAUSE = "com.example.action.AUDIO_PAUSE"
        const val ACTION_TOGGLE = "com.example.action.AUDIO_TOGGLE"
        const val ACTION_NEXT = "com.example.action.AUDIO_NEXT"
        const val ACTION_PREVIOUS = "com.example.action.AUDIO_PREVIOUS"
        const val ACTION_SEEK_INDEX = "com.example.action.AUDIO_SEEK_INDEX"
        const val ACTION_SET_SPEED = "com.example.action.AUDIO_SET_SPEED"
        const val ACTION_SET_VOICE_GENDER = "com.example.action.AUDIO_SET_VOICE_GENDER"
        const val ACTION_STOP = "com.example.action.AUDIO_STOP"

        const val EXTRA_BOOK_ID = "extra_book_id"
        const val EXTRA_BOOK_NAME = "extra_book_name"
        const val EXTRA_CHAPTER = "extra_chapter"
        const val EXTRA_VERSION = "extra_version"
        const val EXTRA_START_INDEX = "extra_start_index"
        const val EXTRA_SPEED = "extra_speed"
        const val EXTRA_VOICE_GENDER = "extra_voice_gender"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val mainHandler = Handler(Looper.getMainLooper())

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private var mediaSession: MediaSession? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var audioManager: AudioManager? = null
    private var audioFocusRequest: AudioFocusRequest? = null

    // Playback state
    private var isPlaying = false
    private var bookId = 1
    private var bookName = "Génesis"
    private var chapter = 1
    private var version = "RVR1960"
    private var currentIndex = 0
    private var speechRate = 1.0f
    private var voiceGender = AudioVoiceGender.FEMALE

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initMediaSession()
        initWakeLock()
        initAudioManager()
        tts = TextToSpeech(applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val ttsEngine = tts
            if (ttsEngine != null) {
                applyVoiceAndSpeed(ttsEngine)

                ttsEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        mainHandler.post {
                            val parsedIndex = utteranceId?.substringAfter("verse_")?.toIntOrNull()
                            if (parsedIndex != null && parsedIndex in BibleAudioController.currentPlaylist.indices) {
                                currentIndex = parsedIndex
                                updateCurrentVerseState()
                                updateNotificationAndMediaSession()

                                // Pre-buffer the next verse seamlessly with QUEUE_ADD for 0ms transition gap
                                if (isPlaying) {
                                    val playlist = BibleAudioController.currentPlaylist
                                    val nextIdx = currentIndex + 1
                                    if (nextIdx in playlist.indices) {
                                        enqueueVerseLookahead(nextIdx)
                                    }
                                }
                            }
                        }
                    }

                    override fun onDone(utteranceId: String?) {
                        mainHandler.post {
                            val parsedIndex = utteranceId?.substringAfter("verse_")?.toIntOrNull()
                            val playlist = BibleAudioController.currentPlaylist

                            if (isPlaying && parsedIndex != null && parsedIndex >= playlist.size - 1) {
                                // Final verse of current chapter completed! Smoothly advance to next chapter
                                advanceToNextChapter()
                            }
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        mainHandler.post {
                            if (isPlaying) {
                                val playlist = BibleAudioController.currentPlaylist
                                if (currentIndex < playlist.size - 1) {
                                    currentIndex++
                                    startStreamingFromCurrentIndex()
                                } else {
                                    advanceToNextChapter()
                                }
                            }
                        }
                    }
                })

                isTtsInitialized = true

                if (isPlaying) {
                    startStreamingFromCurrentIndex()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action ?: return START_NOT_STICKY

        when (action) {
            ACTION_START -> {
                bookId = intent.getIntExtra(EXTRA_BOOK_ID, bookId)
                bookName = intent.getStringExtra(EXTRA_BOOK_NAME) ?: bookName
                chapter = intent.getIntExtra(EXTRA_CHAPTER, chapter)
                version = intent.getStringExtra(EXTRA_VERSION) ?: version
                currentIndex = intent.getIntExtra(EXTRA_START_INDEX, 0)
                speechRate = intent.getFloatExtra(EXTRA_SPEED, 1.0f)
                val genderStr = intent.getStringExtra(EXTRA_VOICE_GENDER)
                if (genderStr != null) {
                    voiceGender = try {
                        AudioVoiceGender.valueOf(genderStr)
                    } catch (e: Exception) {
                        AudioVoiceGender.FEMALE
                    }
                }

                tts?.let { applyVoiceAndSpeed(it) }
                startForegroundWithNotification()
                play()
            }
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
            ACTION_TOGGLE -> if (isPlaying) pause() else play()
            ACTION_NEXT -> skipNext()
            ACTION_PREVIOUS -> skipPrevious()
            ACTION_SEEK_INDEX -> {
                val index = intent.getIntExtra(EXTRA_START_INDEX, currentIndex)
                seekToIndex(index)
            }
            ACTION_SET_SPEED -> {
                val speed = intent.getFloatExtra(EXTRA_SPEED, speechRate)
                setSpeechRateInternal(speed)
            }
            ACTION_SET_VOICE_GENDER -> {
                val genderStr = intent.getStringExtra(EXTRA_VOICE_GENDER)
                if (genderStr != null) {
                    val newGender = try {
                        AudioVoiceGender.valueOf(genderStr)
                    } catch (e: Exception) {
                        AudioVoiceGender.FEMALE
                    }
                    setVoiceGenderInternal(newGender)
                }
            }
            ACTION_STOP -> stopPlayback()
        }

        return START_NOT_STICKY
    }

    private fun play() {
        requestAudioFocus()
        acquireWakeLock()
        isPlaying = true

        tts?.let { applyVoiceAndSpeed(it) }
        updateCurrentVerseState()
        updateNotificationAndMediaSession()

        if (isTtsInitialized) {
            startStreamingFromCurrentIndex()
        }
    }

    private fun pause() {
        isPlaying = false
        tts?.stop()
        releaseWakeLock()

        updateCurrentVerseState()
        updateNotificationAndMediaSession()
    }

    private fun skipNext() {
        val playlist = BibleAudioController.currentPlaylist
        if (currentIndex < playlist.size - 1) {
            currentIndex++
            if (isPlaying) {
                startStreamingFromCurrentIndex()
            } else {
                updateCurrentVerseState()
                updateNotificationAndMediaSession()
            }
        } else {
            advanceToNextChapter()
        }
    }

    private fun skipPrevious() {
        if (currentIndex > 0) {
            currentIndex--
            if (isPlaying) {
                startStreamingFromCurrentIndex()
            } else {
                updateCurrentVerseState()
                updateNotificationAndMediaSession()
            }
        } else {
            if (isPlaying) {
                startStreamingFromCurrentIndex()
            } else {
                updateCurrentVerseState()
                updateNotificationAndMediaSession()
            }
        }
    }

    private fun seekToIndex(index: Int) {
        val playlist = BibleAudioController.currentPlaylist
        if (index in playlist.indices) {
            currentIndex = index
            if (isPlaying) {
                startStreamingFromCurrentIndex()
            } else {
                updateCurrentVerseState()
                updateNotificationAndMediaSession()
            }
        }
    }

    private fun setSpeechRateInternal(rate: Float) {
        speechRate = rate
        tts?.setSpeechRate(speechRate)
        BibleAudioController.updateState { it.copy(speechRate = rate) }

        if (isPlaying) {
            startStreamingFromCurrentIndex()
        }
        updateNotificationAndMediaSession()
    }

    private fun setVoiceGenderInternal(gender: AudioVoiceGender) {
        voiceGender = gender
        tts?.let { applyVoiceAndSpeed(it) }
        BibleAudioController.updateState { it.copy(voiceGender = gender) }

        if (isPlaying) {
            startStreamingFromCurrentIndex()
        }
        updateNotificationAndMediaSession()
    }

    private fun stopPlayback() {
        isPlaying = false
        tts?.stop()
        releaseWakeLock()
        abandonAudioFocus()

        BibleAudioController.updateState {
            it.copy(isPlaying = false, isActive = false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }
        stopSelf()
    }

    /**
     * Starts speaking current verse immediately with QUEUE_FLUSH,
     * and queues the subsequent verse with QUEUE_ADD to achieve 0ms natural flow.
     */
    private fun startStreamingFromCurrentIndex() {
        val playlist = BibleAudioController.currentPlaylist
        if (currentIndex !in playlist.indices) return

        tts?.stop() // Flush any previous utterances

        val currentVerse = playlist[currentIndex]
        val cleanText = BibleAudioSpeechSanitizer.prepareForNaturalSpeech(currentVerse.text)
        if (cleanText.isNotBlank()) {
            val params = Bundle().apply {
                putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
            }
            tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, params, "verse_$currentIndex")

            // Lookahead: enqueue next verse right away so TTS executes with zero hesitation
            val nextIdx = currentIndex + 1
            if (nextIdx in playlist.indices) {
                enqueueVerseLookahead(nextIdx)
            }
        } else {
            if (currentIndex < playlist.size - 1) {
                currentIndex++
                startStreamingFromCurrentIndex()
            }
        }
    }

    private fun enqueueVerseLookahead(index: Int) {
        val playlist = BibleAudioController.currentPlaylist
        if (index !in playlist.indices) return

        val verse = playlist[index]
        val cleanText = BibleAudioSpeechSanitizer.prepareForNaturalSpeech(verse.text)
        if (cleanText.isNotBlank()) {
            val params = Bundle().apply {
                putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
            }
            tts?.speak(cleanText, TextToSpeech.QUEUE_ADD, params, "verse_$index")
        }
    }

    /**
     * Selects high-fidelity Google Neural / HD voices and adjusts prosody pitch.
     */
    private fun applyVoiceAndSpeed(ttsEngine: TextToSpeech) {
        ttsEngine.setSpeechRate(speechRate)

        // Pitch modulation: slightly lower for masculine depth, slightly higher for feminine clarity
        val targetPitch = when (voiceGender) {
            AudioVoiceGender.MALE -> 0.92f
            AudioVoiceGender.FEMALE -> 1.06f
        }
        ttsEngine.setPitch(targetPitch)

        try {
            val allVoices = ttsEngine.voices
            if (!allVoices.isNullOrEmpty()) {
                val spanishVoices = allVoices.filter { it.locale.language.equals("es", ignoreCase = true) }
                val selectedVoice = findBestVoiceForGender(spanishVoices, voiceGender)
                if (selectedVoice != null) {
                    ttsEngine.voice = selectedVoice
                    return
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Fallback locale if voice list isn't accessible
        var langResult = ttsEngine.setLanguage(Locale("es", "ES"))
        if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
            langResult = ttsEngine.setLanguage(Locale("es"))
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                ttsEngine.setLanguage(Locale.getDefault())
            }
        }
    }

    private fun findBestVoiceForGender(spanishVoices: List<Voice>, gender: AudioVoiceGender): Voice? {
        if (spanishVoices.isEmpty()) return null

        val isLookingForFemale = (gender == AudioVoiceGender.FEMALE)

        val candidates = spanishVoices.filter { voice ->
            val name = voice.name.lowercase()
            val features = voice.features?.map { it.lowercase() } ?: emptyList()

            val isExplicitFemale = features.any { it.contains("female") || it.contains("gender=female") } ||
                    name.contains("female") || name.contains("mujer") ||
                    name.contains("-eed") || name.contains("-sfb") || name.contains("-eea") || name.contains("-eee")

            val isExplicitMale = features.any { it.contains("male") || it.contains("gender=male") } ||
                    name.contains("male") || name.contains("hombre") ||
                    name.contains("-eec") || name.contains("-sfa") || name.contains("-eef") || name.contains("-eeb")

            if (isLookingForFemale) {
                isExplicitFemale || (!isExplicitMale && (name.contains("d-") || name.contains("f-") || name.contains("a-")))
            } else {
                isExplicitMale || (!isExplicitFemale && (name.contains("c-") || name.contains("b-") || name.contains("m-")))
            }
        }

        // Rank by neural / network quality and modern dialect
        return candidates.maxByOrNull { voice ->
            var score = 0
            if (voice.name.contains("network", ignoreCase = true)) score += 60
            if (voice.name.contains("neural", ignoreCase = true)) score += 60
            if (voice.quality >= Voice.QUALITY_VERY_HIGH) score += 40
            else if (voice.quality >= Voice.QUALITY_HIGH) score += 20
            if (voice.locale.country.equals("US", ignoreCase = true) || voice.locale.country.equals("MX", ignoreCase = true)) score += 15
            score
        } ?: spanishVoices.firstOrNull()
    }

    private fun advanceToNextChapter() {
        serviceScope.launch {
            val book = BibleCatalog.books.firstOrNull { it.order == bookId }
            val (nextBookId, nextChapter) = when {
                book != null && chapter < book.chaptersCount -> Pair(bookId, chapter + 1)
                bookId < 66 -> Pair(bookId + 1, 1)
                else -> Pair(1, 1) // Loop back or stop
            }

            val nextBook = BibleCatalog.books.firstOrNull { it.order == nextBookId }
            val nextBookName = nextBook?.name ?: "Libro $nextBookId"

            val nextVersesRaw = withContext(Dispatchers.IO) {
                OfflineBibleManager.getVerses(applicationContext, nextBookId, nextChapter)
            }

            if (nextVersesRaw.isNotEmpty()) {
                val newPlaylist = nextVersesRaw.map {
                    AudioVerseItem(
                        bookId = it.bookId,
                        bookName = nextBookName,
                        chapter = it.chapter,
                        verseNumber = it.verseNumber,
                        text = it.text
                    )
                }

                bookId = nextBookId
                bookName = nextBookName
                chapter = nextChapter
                currentIndex = 0

                BibleAudioController.updatePlaylist(newPlaylist)
                updateCurrentVerseState()
                updateNotificationAndMediaSession()

                if (isPlaying) {
                    startStreamingFromCurrentIndex()
                }
            } else {
                stopPlayback()
            }
        }
    }

    private fun updateCurrentVerseState() {
        val playlist = BibleAudioController.currentPlaylist
        val currentVerse = playlist.getOrNull(currentIndex)
        val vNumber = currentVerse?.verseNumber ?: (currentIndex + 1)
        val vText = currentVerse?.text ?: ""

        BibleAudioController.updateState {
            it.copy(
                isPlaying = isPlaying,
                isActive = true,
                currentBookId = bookId,
                currentBookName = bookName,
                currentChapter = chapter,
                currentVerseNumber = vNumber,
                currentVerseText = vText,
                totalVerses = playlist.size,
                currentIndex = currentIndex,
                speechRate = speechRate,
                voiceGender = voiceGender,
                bibleVersion = version
            )
        }
    }

    private fun initMediaSession() {
        mediaSession = MediaSession(applicationContext, "BibleVerseAudioSession").apply {
            setCallback(object : MediaSession.Callback() {
                override fun onPlay() { play() }
                override fun onPause() { pause() }
                override fun onSkipToNext() { skipNext() }
                override fun onSkipToPrevious() { skipPrevious() }
                override fun onStop() { stopPlayback() }
            })
            isActive = true
        }
    }

    private fun updateNotificationAndMediaSession() {
        val playlist = BibleAudioController.currentPlaylist
        val currentVerse = playlist.getOrNull(currentIndex)
        val vNumber = currentVerse?.verseNumber ?: (currentIndex + 1)
        val vText = OfflineBibleManager.cleanVerseText(currentVerse?.text)

        // 1. Update MediaSession state
        val stateBuilder = PlaybackState.Builder()
            .setActions(
                PlaybackState.ACTION_PLAY or
                PlaybackState.ACTION_PAUSE or
                PlaybackState.ACTION_PLAY_PAUSE or
                PlaybackState.ACTION_SKIP_TO_NEXT or
                PlaybackState.ACTION_SKIP_TO_PREVIOUS or
                PlaybackState.ACTION_STOP
            )
            .setState(
                if (isPlaying) PlaybackState.STATE_PLAYING else PlaybackState.STATE_PAUSED,
                currentIndex.toLong(),
                speechRate
            )
        mediaSession?.setPlaybackState(stateBuilder.build())

        // 2. Update MediaSession Metadata
        val metadata = MediaMetadata.Builder()
            .putString(MediaMetadata.METADATA_KEY_TITLE, "$bookName $chapter:$vNumber")
            .putString(MediaMetadata.METADATA_KEY_ARTIST, "Biblia $version • ${voiceGender.displayName}")
            .putString(MediaMetadata.METADATA_KEY_ALBUM, "$bookName $chapter")
            .putString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE, "$bookName $chapter:$vNumber")
            .putString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE, vText)
            .build()
        mediaSession?.setMetadata(metadata)

        // 3. Update Notification
        val notification = buildNotification(vNumber, vText, playlist.size)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun startForegroundWithNotification() {
        val playlist = BibleAudioController.currentPlaylist
        val currentVerse = playlist.getOrNull(currentIndex)
        val vNumber = currentVerse?.verseNumber ?: (currentIndex + 1)
        val vText = OfflineBibleManager.cleanVerseText(currentVerse?.text)

        val notification = buildNotification(vNumber, vText, playlist.size)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun buildNotification(verseNumber: Int, verseText: String, totalVerses: Int): Notification {
        val openAppIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Previous Action
        val prevIntent = Intent(this, BibleAudioService::class.java).apply { action = ACTION_PREVIOUS }
        val prevPendingIntent = PendingIntent.getService(
            this, 1, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Play/Pause Action
        val toggleIntent = Intent(this, BibleAudioService::class.java).apply { action = ACTION_TOGGLE }
        val togglePendingIntent = PendingIntent.getService(
            this, 2, toggleIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Next Action
        val nextIntent = Intent(this, BibleAudioService::class.java).apply { action = ACTION_NEXT }
        val nextPendingIntent = PendingIntent.getService(
            this, 3, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Stop Action
        val stopIntent = Intent(this, BibleAudioService::class.java).apply { action = ACTION_STOP }
        val stopPendingIntent = PendingIntent.getService(
            this, 4, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
        }

        builder.setContentTitle("$bookName $chapter:$verseNumber")
            .setContentText(if (verseText.isNotBlank()) verseText else "Versículo $verseNumber de $totalVerses")
            .setSubText("Biblia $version • ${speechRate}x • ${voiceGender.shortLabel}")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(openAppPendingIntent)
            .setOngoing(isPlaying)
            .setVisibility(Notification.VISIBILITY_PUBLIC)

        // Add media style
        val sessionToken = mediaSession?.sessionToken
        if (sessionToken != null) {
            val mediaStyle = Notification.MediaStyle()
                .setMediaSession(sessionToken)
                .setShowActionsInCompactView(0, 1, 2)
            builder.style = mediaStyle
        }

        // Action 0: Previous
        builder.addAction(
            Notification.Action.Builder(
                Icon.createWithResource(this, android.R.drawable.ic_media_previous),
                "Anterior",
                prevPendingIntent
            ).build()
        )

        // Action 1: Play/Pause
        val playPauseIcon = if (isPlaying) {
            android.R.drawable.ic_media_pause
        } else {
            android.R.drawable.ic_media_play
        }
        val playPauseTitle = if (isPlaying) "Pausar" else "Reproducir"
        builder.addAction(
            Notification.Action.Builder(
                Icon.createWithResource(this, playPauseIcon),
                playPauseTitle,
                togglePendingIntent
            ).build()
        )

        // Action 2: Next
        builder.addAction(
            Notification.Action.Builder(
                Icon.createWithResource(this, android.R.drawable.ic_media_next),
                "Siguiente",
                nextPendingIntent
            ).build()
        )

        // Action 3: Stop / Close
        builder.addAction(
            Notification.Action.Builder(
                Icon.createWithResource(this, android.R.drawable.ic_menu_close_clear_cancel),
                "Cerrar",
                stopPendingIntent
            ).build()
        )

        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reproducción de Audio Bíblico"
            val descriptionText = "Controles de reproducción para escuchar la Biblia con la pantalla apagada o en segundo plano"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setShowBadge(false)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initWakeLock() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BibleVerse:AudioTTSWakeLock").apply {
            setReferenceCounted(false)
        }
    }

    private fun acquireWakeLock() {
        wakeLock?.let {
            if (!it.isHeld) {
                it.acquire(2 * 60 * 60 * 1000L) // 2 hours max safe hold
            }
        }
    }

    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
    }

    private fun initAudioManager() {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun requestAudioFocus() {
        val am = audioManager ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(attributes)
                .setAcceptsDelayedFocusGain(false)
                .setOnAudioFocusChangeListener { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS,
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pause()
                    }
                }
                .build()
            audioFocusRequest?.let { am.requestAudioFocus(it) }
        } else {
            @Suppress("DEPRECATION")
            am.requestAudioFocus(
                { focusChange ->
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        pause()
                    }
                },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }
    }

    private fun abandonAudioFocus() {
        val am = audioManager ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let { am.abandonAudioFocusRequest(it) }
        } else {
            @Suppress("DEPRECATION")
            am.abandonAudioFocus(null)
        }
    }

    override fun onDestroy() {
        isPlaying = false
        tts?.stop()
        tts?.shutdown()
        tts = null

        releaseWakeLock()
        abandonAudioFocus()

        mediaSession?.isActive = false
        mediaSession?.release()
        mediaSession = null

        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
