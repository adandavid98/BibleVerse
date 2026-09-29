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
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.example.MainActivity
import com.example.data.bible.BibleCatalog
import com.example.data.bible.OfflineBibleManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Background Service for Ultra-Realistic Studio Neural Bible Audio.
 * Powered by Google's generative speech engine (24kHz studio PCM) with MediaSession,
 * lock-screen controls, ForegroundService notification, and 0ms lookahead pre-caching.
 */
class BibleAudioService : Service() {

    companion object {
        private const val TAG = "BibleAudioService"
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

    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayJob: Job? = null
    private var prefetchJob: Job? = null

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
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setOnCompletionListener {
                onVersePlaybackCompleted()
            }
            setOnErrorListener { _, what, extra ->
                Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                skipNext()
                true
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

                startForegroundWithNotification()
                playCurrentVerse()
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

        try {
            val player = mediaPlayer
            if (player != null && !player.isPlaying && player.currentPosition > 0) {
                applyPlaybackSpeed(player)
                player.start()
                updateCurrentVerseState(isBuffering = false)
                updateNotificationAndMediaSession()
                triggerLookaheadPrefetch()
                return
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error resuming player directly, restarting verse", e)
        }

        playCurrentVerse()
    }

    private fun pause() {
        isPlaying = false
        currentPlayJob?.cancel()
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error pausing MediaPlayer", e)
        }
        releaseWakeLock()

        updateCurrentVerseState(isBuffering = false)
        updateNotificationAndMediaSession()
    }

    private fun skipNext() {
        val playlist = BibleAudioController.currentPlaylist
        if (currentIndex < playlist.size - 1) {
            currentIndex++
            if (isPlaying) {
                playCurrentVerse()
            } else {
                updateCurrentVerseState(isBuffering = false)
                updateNotificationAndMediaSession()
            }
        } else {
            advanceToNextChapter()
        }
    }

    private fun skipPrevious() {
        if (currentIndex > 0) {
            currentIndex--
        }
        if (isPlaying) {
            playCurrentVerse()
        } else {
            updateCurrentVerseState(isBuffering = false)
            updateNotificationAndMediaSession()
        }
    }

    private fun seekToIndex(index: Int) {
        val playlist = BibleAudioController.currentPlaylist
        if (index in playlist.indices) {
            currentIndex = index
            if (isPlaying) {
                playCurrentVerse()
            } else {
                updateCurrentVerseState(isBuffering = false)
                updateNotificationAndMediaSession()
            }
        }
    }

    private fun setSpeechRateInternal(rate: Float) {
        speechRate = rate
        BibleAudioController.updateState { it.copy(speechRate = rate) }

        mediaPlayer?.let { player ->
            if (isPlaying) {
                applyPlaybackSpeed(player)
            }
        }
        updateNotificationAndMediaSession()
    }

    private fun setVoiceGenderInternal(gender: AudioVoiceGender) {
        if (voiceGender == gender) return
        voiceGender = gender
        BibleAudioController.updateState { it.copy(voiceGender = gender) }

        if (isPlaying) {
            playCurrentVerse()
        }
        updateNotificationAndMediaSession()
    }

    private fun applyPlaybackSpeed(player: MediaPlayer) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val params = player.playbackParams ?: PlaybackParams()
                player.playbackParams = params.setSpeed(speechRate)
            } catch (e: Exception) {
                Log.w(TAG, "Could not set playback speed on MediaPlayer", e)
            }
        }
    }

    private fun stopPlayback() {
        isPlaying = false
        currentPlayJob?.cancel()
        prefetchJob?.cancel()

        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            mediaPlayer?.reset()
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping MediaPlayer", e)
        }

        releaseWakeLock()
        abandonAudioFocus()

        BibleAudioController.updateState {
            it.copy(isPlaying = false, isActive = false, isBuffering = false)
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
     * Fetches studio-quality neural audio for the current verse, plays it with MediaPlayer,
     * and immediately schedules lookahead pre-caching for upcoming verses.
     */
    private fun playCurrentVerse() {
        val playlist = BibleAudioController.currentPlaylist
        if (currentIndex !in playlist.indices) return

        requestAudioFocus()
        acquireWakeLock()
        isPlaying = true

        currentPlayJob?.cancel()
        try {
            mediaPlayer?.reset()
        } catch (e: Exception) {
            Log.w(TAG, "Error resetting MediaPlayer", e)
        }

        val currentVerse = playlist[currentIndex]
        val isCached = StudioAudioGenerator.isVerseCached(
            context = applicationContext,
            version = version,
            bookId = bookId,
            chapter = chapter,
            verseNumber = currentVerse.verseNumber,
            gender = voiceGender
        )

        updateCurrentVerseState(isBuffering = !isCached)
        updateNotificationAndMediaSession()

        currentPlayJob = serviceScope.launch {
            val audioFile = StudioAudioGenerator.getOrGenerateVerseAudio(
                context = applicationContext,
                version = version,
                bookId = bookId,
                chapter = chapter,
                verseNumber = currentVerse.verseNumber,
                rawText = currentVerse.text,
                gender = voiceGender
            )

            if (!coroutineContext.isActive) return@launch

            if (audioFile != null && audioFile.exists()) {
                withContext(Dispatchers.Main) {
                    try {
                        val player = mediaPlayer ?: MediaPlayer().also { mediaPlayer = it }
                        player.reset()
                        player.setDataSource(audioFile.absolutePath)
                        player.setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        player.setOnCompletionListener {
                            onVersePlaybackCompleted()
                        }
                        player.setOnErrorListener { _, what, extra ->
                            Log.e(TAG, "MediaPlayer error playing verse: what=$what, extra=$extra")
                            skipNext()
                            true
                        }
                        player.prepare()
                        applyPlaybackSpeed(player)
                        player.start()

                        isPlaying = true
                        updateCurrentVerseState(isBuffering = false)
                        updateNotificationAndMediaSession()

                        // Trigger prefetch for next 2 verses for seamless 0ms transition
                        triggerLookaheadPrefetch()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error starting MediaPlayer on verse file", e)
                        isPlaying = false
                        updateCurrentVerseState(
                            isBuffering = false,
                            errorMessage = "Error al reproducir audio"
                        )
                        updateNotificationAndMediaSession()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.w(TAG, "Could not obtain studio audio for verse $bookId $chapter:${currentVerse.verseNumber}")
                    // Try next verse or notify
                    if (currentIndex < playlist.size - 1) {
                        currentIndex++
                        playCurrentVerse()
                    } else {
                        isPlaying = false
                        updateCurrentVerseState(
                            isBuffering = false,
                            errorMessage = "No se pudo conectar con el servicio de voz neuronal"
                        )
                        updateNotificationAndMediaSession()
                    }
                }
            }
        }
    }

    /**
     * Background lookahead pre-fetch of the next 2 verses so they are ready on disk
     * before the current verse finishes, delivering 0ms perceived latency.
     */
    private fun triggerLookaheadPrefetch() {
        prefetchJob?.cancel()
        prefetchJob = serviceScope.launch(Dispatchers.IO) {
            val playlist = BibleAudioController.currentPlaylist
            for (offset in 1..2) {
                if (!coroutineContext.isActive) break
                val targetIdx = currentIndex + offset
                if (targetIdx in playlist.indices) {
                    val verse = playlist[targetIdx]
                    StudioAudioGenerator.prefetchVerseAudio(
                        context = applicationContext,
                        version = version,
                        bookId = bookId,
                        chapter = chapter,
                        verseNumber = verse.verseNumber,
                        rawText = verse.text,
                        gender = voiceGender
                    )
                }
            }
        }
    }

    private fun onVersePlaybackCompleted() {
        val playlist = BibleAudioController.currentPlaylist
        if (currentIndex < playlist.size - 1) {
            currentIndex++
            playCurrentVerse()
        } else {
            advanceToNextChapter()
        }
    }

    private fun advanceToNextChapter() {
        serviceScope.launch {
            val book = BibleCatalog.books.firstOrNull { it.order == bookId }
            val (nextBookId, nextChapter) = when {
                book != null && chapter < book.chaptersCount -> Pair(bookId, chapter + 1)
                bookId < 66 -> Pair(bookId + 1, 1)
                else -> Pair(1, 1)
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
                updateCurrentVerseState(isBuffering = true)
                updateNotificationAndMediaSession()

                if (isPlaying) {
                    playCurrentVerse()
                }
            } else {
                stopPlayback()
            }
        }
    }

    private fun updateCurrentVerseState(isBuffering: Boolean = false, errorMessage: String? = null) {
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
                bibleVersion = version,
                isBuffering = isBuffering,
                errorMessage = errorMessage
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
            .putString(MediaMetadata.METADATA_KEY_ARTIST, "Biblia $version • ${voiceGender.displayName} (Estudio)")
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
            .setSubText("Biblia $version • ${speechRate}x • ${voiceGender.shortLabel} Neural")
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
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BibleVerse:StudioAudioWakeLock").apply {
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
        currentPlayJob?.cancel()
        prefetchJob?.cancel()

        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.w(TAG, "Error releasing MediaPlayer", e)
        }

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
