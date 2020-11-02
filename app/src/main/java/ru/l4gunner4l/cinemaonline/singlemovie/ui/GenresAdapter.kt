package ru.l4gunner4l.cinemaonline.singlemovie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_genre.*
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.data.remote.model.GenreRemoteModel

class GenresAdapter(private val list: List<GenreRemoteModel>) :
    RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class GenreViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(genre: GenreRemoteModel) {
            itemTvGenre.text = genre.name.toLowerCase()
        }

    }
}
