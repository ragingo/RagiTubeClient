package com.ragingo.ragitube.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ragingo.ragitube.databinding.ListItemVideoBinding
import com.ragingo.ragitube.viewModels.VideoListItemViewModel

class VideoListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val values: ObservableArrayList<VideoListItemViewModel>
) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    init {
        values.addOnListChangedCallback(object :
            ObservableList.OnListChangedCallback<ObservableArrayList<VideoListItemViewModel>>() {
            override fun onChanged(sender: ObservableArrayList<VideoListItemViewModel>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(
                sender: ObservableArrayList<VideoListItemViewModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<VideoListItemViewModel>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
            }

            override fun onItemRangeInserted(
                sender: ObservableArrayList<VideoListItemViewModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(
                sender: ObservableArrayList<VideoListItemViewModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemVideoBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vm = values[position]
    }

    inner class ViewHolder(private val binding: ListItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var vm: VideoListItemViewModel?
            get() = binding.vm
            set(value) {
                binding.vm = value
            }
    }
}
