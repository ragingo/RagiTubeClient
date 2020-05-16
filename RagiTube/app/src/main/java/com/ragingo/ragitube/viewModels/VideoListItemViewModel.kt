package com.ragingo.ragitube.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoListItemViewModel : ViewModel() {
    var title = MutableLiveData("")
    var thumbnailUrl = MutableLiveData("")
}
