package com.ragingo.ragitube.viewModels

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.ragingo.ragitube.models.api.YouTubeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.simpleName
    }

    val videos = ObservableArrayList<VideoListItemViewModel>()

    fun loadVideos(context: Context, keyword: String, maxCount: Int) {
        val client = YouTubeApiClient(context)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val res = client.searchByKeyword("snippet", "video", keyword, maxCount)
                res.items.forEach {
                    val item = VideoListItemViewModel()
                    item.title.value = it.snippet.title
                    videos.add(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
