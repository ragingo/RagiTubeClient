package com.ragingo.ragitube.viewModels

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import com.ragingo.ragitube.models.repos.YouTubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {
    private lateinit var repo: YouTubeRepository
    private lateinit var lifecycleOwner: LifecycleOwner

    var isInitialized : Boolean = false
        private set
    val isLoading = MutableLiveData(false)
    val videos = ObservableArrayList<VideoListItemViewModel>()
    val selectedItem: MutableLiveData<VideoListItemViewModel?> = MutableLiveData(null)

    fun init(context: Context, lifecycleOwner: LifecycleOwner) {
        this.isInitialized = true
        this.repo = YouTubeRepository(context)
        this.lifecycleOwner = lifecycleOwner

        // debug
        GlobalScope.launch {
            repo.getSearchHistories().forEach {
                println(it)
            }
        }
    }

    fun setup(keyword: String, maxCount: Int) {
        check(isInitialized)
        search(keyword, maxCount)
    }

    private fun search(keyword: String, maxCount: Int) {
        videos.clear()
        selectedItem.value = null

        GlobalScope.launch(Dispatchers.Main) {
            try {
                isLoading.value = true

                val res = repo.searchVideosByKeyword(keyword, maxCount)
                res.items.forEach {
                    val item = VideoListItemViewModel.create(it)
                    item.isSelected.observe(lifecycleOwner, { value ->
                        if (!value) {
                            return@observe
                        }
                        if (selectedItem.value !== item) {
                            selectedItem.value = item
                        }
                        item.unselect()
                    })
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
