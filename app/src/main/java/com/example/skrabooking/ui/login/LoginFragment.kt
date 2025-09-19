package com.example.skrabooking.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.skrabooking.R
import com.example.skrabooking.data.SettingsRepository
import com.example.skrabooking.ui.main.MainFragment
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private val settingsRepository: SettingsRepository by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginView(
                    onClickLogin = {
                        settingsRepository.setIsLoggedIn(true)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentsContainer, MainFragment(), null)
                            .commit()
                    }
                )
            }
        }
    }
}