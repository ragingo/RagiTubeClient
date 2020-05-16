package com.ragingo.ragitube.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ragingo.ragitube.models.services.youtube.search.SearchListItem

class VideoListItemViewModel : ViewModel() {
    var isSelected = MutableLiveData(false)

    var videoId = MutableLiveData("")
    var title = MutableLiveData("")
    var thumbnailUrl = MutableLiveData("")

    companion object {
        fun create(model: SearchListItem) : VideoListItemViewModel {
            val vm = VideoListItemViewModel()
            vm.videoId.value = model.id.videoId
            vm.title.value = model.snippet.title
            vm.thumbnailUrl.value = model.snippet.thumbnails.default.url
            return vm
        }
    }

    fun select() {
        isSelected.value = true
    }

    fun unselect() {
        isSelected.value = false
    }
}
