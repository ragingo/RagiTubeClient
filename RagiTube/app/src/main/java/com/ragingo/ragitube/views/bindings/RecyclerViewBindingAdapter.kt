package com.ragingo.ragitube.views.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapter {
    @BindingAdapter("adapter")
    @JvmStatic
    fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
    }
}
