package com.ragingo.ragitube.views.bindings

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object AppCompatImageViewBindingAdapter {
    @BindingAdapter("imageURI")
    @JvmStatic
    fun setImageURI(view: AppCompatImageView, imageURI: String?) {
        if (imageURI == null || imageURI.isEmpty()) {
            return
        }
        Glide.with(view).load(imageURI).into(view)
    }
}