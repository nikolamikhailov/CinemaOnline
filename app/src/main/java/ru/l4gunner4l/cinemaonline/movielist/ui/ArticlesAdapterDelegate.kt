package ru.l4gunner4l.cinemaonline.movielist.ui

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.base.ListItem
import ru.l4gunner4l.cinemaonline.movielist.ui.model.MovieListItem

fun moviesAdapterDelegate(onClick: (Int) -> Unit): AdapterDelegate<List<ListItem>> {
    return adapterDelegateLayoutContainer<MovieListItem, ListItem>(
        R.layout.item_movie
    ) {
        containerView.setOnClickListener {
            onClick(adapterPosition)
        }
        bind {
            with(item) {
                itemMovieTitle.text = title
                itemMovieOverview.text = overview
                Glide.with(containerView)
                    .load(image)
                    .into(itemMovieImage)
            }
        }
    }
}