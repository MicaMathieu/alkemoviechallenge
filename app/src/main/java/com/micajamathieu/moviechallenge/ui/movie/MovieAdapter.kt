package com.micajamathieu.moviechallenge.ui.movie

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.micajamathieu.moviechallenge.R
import com.micajamathieu.moviechallenge.data.model.Movie

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onClickListener: (Movie) -> Unit
) :RecyclerView.Adapter<MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       val item = movieList[position]
        //pasamos tambien onclicklistener
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int {
       return movieList.size
    }

}