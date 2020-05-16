package com.ragingo.ragitube.viewModels

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import com.ragingo.ragitube.models.api.YouTubeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.simpleName
    }

    var lifecycleOwner: LifecycleOwner? = null
    val isLoading = MutableLiveData(false)
    val videos = ObservableArrayList<VideoListItemViewModel>()
    val selectedItemViewModel: MutableLiveData<VideoListItemViewModel?> = MutableLiveData(null)

    fun loadVideos(context: Context, keyword: String, maxCount: Int) {
        videos.clear()
        selectedItemViewModel.value = null

        val client = YouTubeApiClient(context)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                isLoading.value = true

                val res = client.searchByKeyword("snippet", "video", keyword, maxCount)
                res.items.forEach {
                    val item = VideoListItemViewModel.create(it)

                    if (lifecycleOwner != null) {
                        item.isSelected.observe(lifecycleOwner!!, { value ->
                            if (!value) {
                                return@observe
                            }
                            if (selectedItemViewModel.value !== item) {
                                selectedItemViewModel.value = item
                            }
                            item.unselect()
                        })
                    }
                    videos.add(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}
