package com.micajamathieu.moviechallenge.data.datasource

import com.micajamathieu.moviechallenge.data.model.APIResponse
import com.micajamathieu.moviechallenge.data.model.Movie
import com.micajamathieu.moviechallenge.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataSource @Inject constructor(private val api: IDataSource): BaseDataSource(){



    suspend fun getMovies(): Resource<List<Movie>>{
        return getResult {
            api.getPopularMovies(Constants.API_KEY)
        }
    }

    suspend fun getMoviesByName(movieName: String): Resource<List<Movie>>{
        return getResult {
            api.getMovieByName(Constants.API_KEY,movieName)
        }
    }

}


