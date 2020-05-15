package com.ragingo.ragitube.viewModels

import androidx.lifecycle.ViewModel
import com.ragingo.ragitube.views.adapters.VideoListAdapter

class MainViewModel : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.simpleName
    }

    val adapter = VideoListAdapter()
}
