package com.ragingo.ragitube.views.converters

import android.view.View
import androidx.databinding.BindingConversion

object ViewConverters {
    @BindingConversion
    @JvmStatic
    fun booleanToVisibility(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }
}
