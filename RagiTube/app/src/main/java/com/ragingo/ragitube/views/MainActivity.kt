package com.ragingo.ragitube.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

        vm.loadVideos(this, "あつ森", 20)

//        val videoId = "mw5VIEIvuMI"
//        val intent = YouTubeStandalonePlayer.createVideoIntent(this, apiKey, videoId);
//        startActivity(intent);
    }
}
