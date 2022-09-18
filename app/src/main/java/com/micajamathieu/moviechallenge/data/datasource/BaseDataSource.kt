package com.micajamathieu.moviechallenge.data.datasource

import com.micajamathieu.moviechallenge.R
import com.micajamathieu.moviechallenge.data.model.APIResponse
import retrofit2.Response
import java.io.Serializable

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<APIResponse<T>>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.movies.let {
                    return Resource.success(it)
                }

            }
            return Resource.error(R.string.no_result)
        } catch (e: Exception) {
            return Resource.error(R.string.no_internet)
        }
    }
}

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val resId: Int = 0
) : Serializable {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data
            )
        }

        fun <T> error(resId: Int, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                resId
            )
        }
    }
}