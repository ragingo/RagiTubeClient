package com.ragingo.ragitube.views.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.FragmentHomeBinding
import com.ragingo.ragitube.viewModels.HomeViewModel
import com.ragingo.ragitube.views.adapters.VideoListAdapter

class HomeFragment : Fragment() {
    private val vm: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.adapter = VideoListAdapter(this, vm.videos)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
