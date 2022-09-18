package com.micajamathieu.moviechallenge.data.model

import com.google.gson.annotations.SerializedName


data class APIResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: T,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

