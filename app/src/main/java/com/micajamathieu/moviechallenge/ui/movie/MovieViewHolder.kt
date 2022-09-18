package com.micajamathieu.moviechallenge.ui.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.micajamathieu.moviechallenge.data.model.Movie
import com.micajamathieu.moviechallenge.databinding.ItemMovieBinding
import com.micajamathieu.moviechallenge.util.Constants

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemMovieBinding.bind(view)


    fun render(movie: Movie, onClickListener:(Movie) -> Unit){
        binding.tvTittle.text = movie.title
        Glide.with(binding.ivMovie.context).load(Constants.BASE_IMG_URL + movie.posterPath).into(binding.ivMovie)
        itemView.setOnClickListener{onClickListener(movie)}

    }
}