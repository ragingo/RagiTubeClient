package com.ragingo.ragitube.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ragingo.ragitube.R
import com.ragingo.ragitube.databinding.ActivityMainBinding
import com.ragingo.ragitube.models.api.YouTubeApiClient
import com.ragingo.ragitube.viewModels.MainViewModel
import com.ragingo.ragitube.viewModels.VideoListItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this


        val client = YouTubeApiClient(this)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val res = client.searchByKeyword("snippet", "video", "あつ森", 10)
                println(res.kind)
                res.items.forEach {
                    val itemVM = VideoListItemViewModel()
                    itemVM.title.value = it.snippet.title
                    vm.adapter.addValue(itemVM)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        val videoId = "mw5VIEIvuMI"
//        val intent = YouTubeStandalonePlayer.createVideoIntent(this, apiKey, videoId);
//        startActivity(intent);
    }
}
