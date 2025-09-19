package com.example.skrabooking.ui.my_books

import android.app.Application
import android.media.MediaPlayer
import com.example.skrabooking.R

class AudioPlayerManager(private val application: Application) {

    private var mediaPlayer = MediaPlayer.create(application, R.raw.danko1)
    private var lastTrackResId: Int? = null
    private var isAppStopped = false

    fun startLastTrack() {
        if (!isAppStopped) return
        isAppStopped = false
        lastTrackResId?.let {
            playMusic(it)
        }
    }

    fun playMusic(resId: Int) {
        lastTrackResId = resId
        mediaPlayer = MediaPlayer.create(application, resId)
        mediaPlayer.start()
    }

    fun resetMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun resetMusicBecauseAppStopped() {
        resetMusic()
        isAppStopped = true
    }
}