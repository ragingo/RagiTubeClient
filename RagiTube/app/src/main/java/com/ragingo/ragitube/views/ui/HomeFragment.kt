package com.ragingo.ragitube.views.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.FragmentHomeBinding
import com.ragingo.ragitube.viewModels.HomeViewModel
import com.ragingo.ragitube.views.adapters.VideoListAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.adapter = VideoListAdapter(this, vm.videos)

        vm.lifecycleOwner = this
        vm.selectedItemViewModel.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }
            val apiKey = getString(R.string.google_api_key)
            val intent = YouTubeStandalonePlayer.createVideoIntent(
                activity,
                apiKey,
                it.videoId.value,
                0,
                true,
                true
            )
            startActivity(intent)
        })
        vm.loadVideos(requireContext(), "あつ森", 20)
    }
}
