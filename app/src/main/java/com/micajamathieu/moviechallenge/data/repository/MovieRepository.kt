package com.micajamathieu.moviechallenge.data.repository

import com.micajamathieu.moviechallenge.data.datasource.DataSource
import com.micajamathieu.moviechallenge.data.datasource.Resource
import com.micajamathieu.moviechallenge.data.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: DataSource) {


    suspend fun getAllMovies(): Resource<List<Movie>> {
        return api.getMovies()

    }

    suspend fun getMovieByName(movieName: String): Resource<List<Movie>>{
        return api.getMoviesByName(movieName)
    }
}