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
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Professional Hands-Free Background Bible Audio Service.
 * Plays official studio recordings per chapter as continuous "song-like" audio tracks.
 * Supports instant pause/resume, scrubber seeking, 10s rewind/forward, lock-screen controls,
 * and automatic chapter-to-chapter continuous progression.
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
        const val ACTION_SEEK_POSITION = "com.example.action.AUDIO_SEEK_POSITION"
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
        const val EXTRA_SEEK_POSITION_MS = "extra_seek_position_ms"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var mediaPlayer: MediaPlayer? = null
    private var loadJob: Job? = null
    private var progressTickerJob: Job? = null

    private var mediaSession: MediaSession? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var audioManager: AudioManager? = null
    private var audioFocusRequest: AudioFocusRequest? = null

    // Playback state
    private var isPlaying = false
    private var isPlayerPrepared = false
    private var bookId = 1
    private var bookName = "Génesis"
    private var chapter = 1
    private var version = "RVR1960"
    private var currentIndex = 0
    private var speechRate = 1.0f
    private var voiceGender = AudioVoiceGender.FEMALE
    private var currentPositionMs = 0L
    private var totalDurationMs = 0L

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
                onChapterPlaybackCompleted()
            }
            setOnErrorListener { _, what, extra ->
                Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                this@BibleAudioService.isPlayerPrepared = false
                this@BibleAudioService.isPlaying = false
                updateCurrentVerseState(
                    isBuffering = false,
                    errorMessage = "Error en reproducción de audio"
                )
                updateNotificationAndMediaSession()
                true
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action ?: return START_NOT_STICKY

        when (action) {
            ACTION_START -> {
                val newBookId = intent.getIntExtra(EXTRA_BOOK_ID, bookId)
                val newBookName = intent.getStringExtra(EXTRA_BOOK_NAME) ?: bookName
                val newChapter = intent.getIntExtra(EXTRA_CHAPTER, chapter)
                val newVersion = intent.getStringExtra(EXTRA_VERSION) ?: version
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

                val chapterChanged = (newBookId != bookId || newChapter != chapter || newVersion != version)
                bookId = newBookId
                bookName = newBookName
                chapter = newChapter
                version = newVersion

                startForegroundWithNotification()
                if (chapterChanged || mediaPlayer == null || totalDurationMs == 0L) {
                    loadAndPlayChapter()
                } else {
                    play()
                }
            }
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
            ACTION_TOGGLE -> if (isPlaying) pause() else play()
            ACTION_NEXT -> advanceToNextChapter()
            ACTION_PREVIOUS -> previousOrRewind()
            ACTION_SEEK_POSITION -> {
                val posMs = intent.getLongExtra(EXTRA_SEEK_POSITION_MS, 0L)
                seekToPositionInternal(posMs)
            }
            ACTION_SEEK_INDEX -> {
                val index = intent.getIntExtra(EXTRA_START_INDEX, currentIndex)
                currentIndex = index
                updateCurrentVerseState(isBuffering = false)
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
                    if (voiceGender != newGender) {
                        voiceGender = newGender
                        BibleAudioController.updateState { it.copy(voiceGender = newGender) }
                        if (isPlaying) {
                            loadAndPlayChapter()
                        }
                    }
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

        val player = mediaPlayer
        if (player != null && isPlayerPrepared) {
            try {
                applyPlaybackSpeed(player)
                player.start()
                startProgressTicker()
                updateCurrentVerseState(isBuffering = false)
                updateNotificationAndMediaSession()
                return
            } catch (e: Exception) {
                Log.w(TAG, "Error resuming player, reloading chapter", e)
                isPlayerPrepared = false
            }
        }

        loadAndPlayChapter()
    }

    private fun pause() {
        isPlaying = false
        stopProgressTicker()
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

    private fun previousOrRewind() {
        val player = mediaPlayer
        if (player != null && player.currentPosition > 5000) {
            // If already played more than 5s, rewind to start of current chapter
            player.seekTo(0)
            currentPositionMs = 0L
            updateCurrentVerseState(isBuffering = false)
            updateNotificationAndMediaSession()
        } else {
            // Go to previous chapter
            advanceToPreviousChapter()
        }
    }

    private fun seekToPositionInternal(posMs: Long) {
        val player = mediaPlayer
        if (player != null && (totalDurationMs > 0L || isPlayerPrepared)) {
            val maxDur = if (totalDurationMs > 0L) totalDurationMs else player.duration.toLong().coerceAtLeast(0L)
            val clamped = if (maxDur > 0L) posMs.coerceIn(0L, maxDur) else posMs
            player.seekTo(clamped.toInt())
            currentPositionMs = clamped
            updateCurrentVerseState(isBuffering = false)
            updateNotificationAndMediaSession()
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

    /**
     * Loads the official studio recorded audio track for the entire chapter (YouVersion CDN).
     * Plays seamlessly as a single song track.
     */
    private fun loadAndPlayChapter() {
        requestAudioFocus()
        acquireWakeLock()
        isPlaying = true
        isPlayerPrepared = false

        loadJob?.cancel()
        stopProgressTicker()

        try {
            mediaPlayer?.reset()
        } catch (e: Exception) {
            Log.w(TAG, "Error resetting MediaPlayer", e)
        }

        updateCurrentVerseState(isBuffering = true)
        updateNotificationAndMediaSession()

        loadJob = serviceScope.launch {
            // Official studio recorded human narration (Faith Comes By Hearing / YouVersion CDN)
            // Checks local offline cache first, else streams CDN URL and downloads for permanent offline playback
            val audioSource = OfficialAudioResolver.resolveChapterAudio(
                context = applicationContext,
                version = version,
                bookOrder = bookId,
                chapter = chapter
            )

            if (!coroutineContext.isActive) return@launch

            if (audioSource != null) {
                withContext(Dispatchers.Main) {
                    try {
                        val player = mediaPlayer ?: MediaPlayer().also { mediaPlayer = it }
                        player.reset()
                        isPlayerPrepared = false
                        player.setDataSource(audioSource)
                        player.setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        player.setOnCompletionListener {
                            onChapterPlaybackCompleted()
                        }
                        player.setOnErrorListener { _, what, extra ->
                            Log.e(TAG, "MediaPlayer error playing chapter: what=$what, extra=$extra")
                            isPlayerPrepared = false
                            isPlaying = false
                            updateCurrentVerseState(
                                isBuffering = false,
                                errorMessage = "Error al reproducir audio del capítulo"
                            )
                            updateNotificationAndMediaSession()
                            true
                        }

                        // Prepare asynchronously so main UI thread is never blocked
                        player.setOnPreparedListener { mp ->
                            isPlayerPrepared = true
                            totalDurationMs = mp.duration.toLong().coerceAtLeast(0L)
                            currentPositionMs = 0L

                            applyPlaybackSpeed(mp)
                            mp.start()
                            isPlaying = true

                            startProgressTicker()
                            updateCurrentVerseState(isBuffering = false)
                            updateNotificationAndMediaSession()
                        }
                        player.prepareAsync()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error starting MediaPlayer on chapter audio", e)
                        isPlaying = false
                        isPlayerPrepared = false
                        updateCurrentVerseState(
                            isBuffering = false,
                            errorMessage = "Error al iniciar reproducción del capítulo"
                        )
                        updateNotificationAndMediaSession()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.w(TAG, "Audio not available for $bookName $chapter ($version)")
                    isPlaying = false
                    isPlayerPrepared = false
                    updateCurrentVerseState(
                        isBuffering = false,
                        errorMessage = "Audio no disponible para este capítulo. Comprueba tu conexión."
                    )
                    updateNotificationAndMediaSession()
                }
            }
        }
    }

    private fun startProgressTicker() {
        progressTickerJob?.cancel()
        progressTickerJob = serviceScope.launch {
            while (isActive && isPlaying) {
                val player = mediaPlayer
                if (player != null && player.isPlaying) {
                    try {
                        currentPositionMs = player.currentPosition.toLong()
                        val dur = player.duration.toLong()
                        if (dur > 0L) totalDurationMs = dur

                        BibleAudioController.updateState {
                            it.copy(
                                isPlaying = true,
                                currentPositionMs = currentPositionMs,
                                totalDurationMs = totalDurationMs
                            )
                        }
                    } catch (e: Exception) {
                        // ignore state errors during seek
                    }
                }
                delay(500)
            }
        }
    }

    private fun stopProgressTicker() {
        progressTickerJob?.cancel()
        progressTickerJob = null
    }

    private fun onChapterPlaybackCompleted() {
        stopProgressTicker()
        advanceToNextChapter()
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

            bookId = nextBookId
            bookName = nextBookName
            chapter = nextChapter
            currentIndex = 0
            currentPositionMs = 0L
            totalDurationMs = 0L

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
                BibleAudioController.updatePlaylist(newPlaylist)
            }

            updateCurrentVerseState(isBuffering = true)
            updateNotificationAndMediaSession()

            if (isPlaying) {
                loadAndPlayChapter()
            }
        }
    }

    private fun advanceToPreviousChapter() {
        serviceScope.launch {
            val (prevBookId, prevChapter) = when {
                chapter > 1 -> Pair(bookId, chapter - 1)
                bookId > 1 -> {
                    val prevBook = BibleCatalog.books.firstOrNull { it.order == bookId - 1 }
                    Pair(bookId - 1, prevBook?.chaptersCount ?: 1)
                }
                else -> Pair(66, 22)
            }

            val prevBook = BibleCatalog.books.firstOrNull { it.order == prevBookId }
            val prevBookName = prevBook?.name ?: "Libro $prevBookId"

            val prevVersesRaw = withContext(Dispatchers.IO) {
                OfflineBibleManager.getVerses(applicationContext, prevBookId, prevChapter)
            }

            bookId = prevBookId
            bookName = prevBookName
            chapter = prevChapter
            currentIndex = 0
            currentPositionMs = 0L
            totalDurationMs = 0L

            if (prevVersesRaw.isNotEmpty()) {
                val newPlaylist = prevVersesRaw.map {
                    AudioVerseItem(
                        bookId = it.bookId,
                        bookName = prevBookName,
                        chapter = it.chapter,
                        verseNumber = it.verseNumber,
                        text = it.text
                    )
                }
                BibleAudioController.updatePlaylist(newPlaylist)
            }

            updateCurrentVerseState(isBuffering = true)
            updateNotificationAndMediaSession()

            if (isPlaying) {
                loadAndPlayChapter()
            }
        }
    }

    private fun stopPlayback() {
        isPlaying = false
        loadJob?.cancel()
        stopProgressTicker()

        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            isPlayerPrepared = false
            mediaPlayer?.reset()
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping MediaPlayer", e)
        }

        releaseWakeLock()
        abandonAudioFocus()

        BibleAudioController.updateState {
            it.copy(
                isPlaying = false,
                isActive = false,
                isBuffering = false,
                currentPositionMs = 0L,
                totalDurationMs = 0L
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }
        stopSelf()
    }

    private fun updateCurrentVerseState(isBuffering: Boolean = false, errorMessage: String? = null) {
        val playlist = BibleAudioController.currentPlaylist
        val currentVerse = playlist.getOrNull(currentIndex)
        val vNumber = currentVerse?.verseNumber ?: 1
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
                errorMessage = errorMessage,
                currentPositionMs = currentPositionMs,
                totalDurationMs = totalDurationMs
            )
        }
    }

    private fun initMediaSession() {
        mediaSession = MediaSession(applicationContext, "BibleVerseAudioSession").apply {
            setCallback(object : MediaSession.Callback() {
                override fun onPlay() { play() }
                override fun onPause() { pause() }
                override fun onSkipToNext() { advanceToNextChapter() }
                override fun onSkipToPrevious() { previousOrRewind() }
                override fun onSeekTo(pos: Long) { seekToPositionInternal(pos) }
                override fun onStop() { stopPlayback() }
            })
            isActive = true
        }
    }

    private fun updateNotificationAndMediaSession() {
        // 1. Update MediaSession state with duration and current position
        val stateBuilder = PlaybackState.Builder()
            .setActions(
                PlaybackState.ACTION_PLAY or
                PlaybackState.ACTION_PAUSE or
                PlaybackState.ACTION_PLAY_PAUSE or
                PlaybackState.ACTION_SKIP_TO_NEXT or
                PlaybackState.ACTION_SKIP_TO_PREVIOUS or
                PlaybackState.ACTION_SEEK_TO or
                PlaybackState.ACTION_STOP
            )
            .setState(
                if (isPlaying) PlaybackState.STATE_PLAYING else PlaybackState.STATE_PAUSED,
                currentPositionMs,
                speechRate
            )
        mediaSession?.setPlaybackState(stateBuilder.build())

        // 2. Update MediaSession Metadata
        val metadata = MediaMetadata.Builder()
            .putString(MediaMetadata.METADATA_KEY_TITLE, "$bookName $chapter")
            .putString(MediaMetadata.METADATA_KEY_ARTIST, "Biblia $version • Voz Humana IA")
            .putString(MediaMetadata.METADATA_KEY_ALBUM, bookName)
            .putString(MediaMetadata.METADATA_KEY_DISPLAY_TITLE, "$bookName $chapter")
            .putString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE, "Capítulo completo • Biblia $version")
            .putLong(MediaMetadata.METADATA_KEY_DURATION, totalDurationMs)
            .build()
        mediaSession?.setMetadata(metadata)

        // 3. Update Notification
        val notification = buildNotification()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun startForegroundWithNotification() {
        val notification = buildNotification()

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

    private fun buildNotification(): Notification {
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

        val posStr = BibleAudioState.formatTime(currentPositionMs)
        val durStr = BibleAudioState.formatTime(totalDurationMs)

        builder.setContentTitle("$bookName $chapter")
            .setContentText("Biblia $version • $posStr / $durStr")
            .setSubText("${speechRate}x • Audio Narrado Oficial")
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
            val descriptionText = "Controles de reproducción oficial para escuchar la Biblia con la pantalla apagada o en segundo plano"
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
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BibleVerse:OfficialAudioWakeLock").apply {
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
        loadJob?.cancel()
        stopProgressTicker()

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
