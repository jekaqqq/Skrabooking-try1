package com.example.skrabooking.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.skrabooking.ui.my_books.AudioPlayerManager
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val audioPlayerManager: AudioPlayerManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainView()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        audioPlayerManager.startLastTrack()
    }

    override fun onStop() {
        super.onStop()
        audioPlayerManager.resetMusicBecauseAppStopped()
    }
}