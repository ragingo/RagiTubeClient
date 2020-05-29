package com.ragingo.ragitube.views.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.FragmentSettingsBinding
import com.ragingo.ragitube.viewModels.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val vm: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSettingsBinding.bind(view)
        binding.lifecycleOwner = this
        binding.vm = vm

        button_license.setOnClickListener {
            findNavController().navigate(R.id.action_nav_settings_to_nav_license)
        }
    }
}
