package com.example.skrabooking.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.skrabooking.R
import com.example.skrabooking.data.SettingsRepository
import com.example.skrabooking.ui.login.LoginFragment
import com.example.skrabooking.ui.main.MainFragment
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class ContainerActivity : FragmentActivity(), KoinComponent {

    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        val fragmentToSet = if (settingsRepository.isLoggedIn()) {
            MainFragment()
        } else {
            LoginFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentsContainer, fragmentToSet, null)
            .commitAllowingStateLoss()
    }
}