package com.bignerdranch.android.employees.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bignerdranch.android.employees.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.activity?.title = "Settings"
    }
}