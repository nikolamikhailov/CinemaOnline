package ru.l4gunner4l.cinemaonline.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialization(view, savedInstanceState == null)
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initialization(view: View, isFirstInitialization: Boolean)
}