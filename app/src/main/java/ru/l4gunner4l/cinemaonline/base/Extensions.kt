package ru.l4gunner4l.cinemaonline.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun RecyclerView.setAdapterAndCleanupOnDetachFromWindow(mAdapter: RecyclerView.Adapter<*>) {
    adapter = mAdapter
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
        }

        override fun onViewDetachedFromWindow(v: View?) {
            adapter = null
            removeOnAttachStateChangeListener(this)
        }
    })
}