package com.ragingo.ragitube.views.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.FragmentSearchResultBinding
import com.ragingo.ragitube.viewModels.SearchResultViewModel
import com.ragingo.ragitube.views.adapters.VideoListAdapter

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {
    private val args: SearchResultFragmentArgs by navArgs()
    private val vm: SearchResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchResultBinding.bind(view)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.adapter = VideoListAdapter(this, vm.videos)

        if (!vm.isInitialized) {
            vm.init(requireContext(), this)
            vm.selectedItem.observe(viewLifecycleOwner, Observer {
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
            vm.setup(args.keyword, 20)
        }
    }
}
