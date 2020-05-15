package com.ragingo.ragitube.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ragingo.ragitube.databinding.ListItemVideoBinding
import com.ragingo.ragitube.viewModels.VideoListItemViewModel

class VideoListAdapter(
) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    private val values = mutableListOf<VideoListItemViewModel>()

    fun addValue(vm: VideoListItemViewModel) {
        values.add(vm)
        notifyItemInserted(values.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemVideoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vm.title.value = values[position].title.value ?: ""
    }

    inner class ViewHolder(binding: ListItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        private val itemVM = VideoListItemViewModel()
        val vm: VideoListItemViewModel by lazy { itemVM }

        init {
            binding.vm = itemVM
        }
    }
}
