package com.micajamathieu.moviechallenge.data.datasource

import com.micajamathieu.moviechallenge.data.model.APIResponse
import com.micajamathieu.moviechallenge.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String
    ): Response<APIResponse<List<Movie>>>

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("api_key") api_key: String,
        @Query("query") movieName: String
    ): Response<APIResponse<List<Movie>>>

}

