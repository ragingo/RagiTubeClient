package com.ragingo.ragitube.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.ActivityMainBinding
import com.ragingo.ragitube.viewModels.MainViewModel
import com.ragingo.ragitube.views.adapters.VideoListAdapter

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = vm
        binding.adapter = VideoListAdapter(this, vm.videos)

        vm.lifecycleOwner = this
        vm.selectedItemViewModel.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            val apiKey = getString(R.string.google_api_key)
            val intent = YouTubeStandalonePlayer.createVideoIntent(
                this,
                apiKey,
                it.videoId.value,
                0,
                true,
                true
            )
            startActivity(intent)
        })
        vm.loadVideos(this, "あつ森", 20)
    }
}
