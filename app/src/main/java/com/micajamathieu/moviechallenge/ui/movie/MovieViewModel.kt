package com.micajamathieu.moviechallenge.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micajamathieu.moviechallenge.R
import com.micajamathieu.moviechallenge.data.datasource.Resource
import com.micajamathieu.moviechallenge.data.model.Movie
import com.micajamathieu.moviechallenge.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    //manejando loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //manejando errores
    private val _isError = MutableLiveData<Int>()
    val isError: LiveData<Int> = _isError

    fun getPopularMovies() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                repository.getAllMovies()
            }.run {
                when (this.status) {
                    Resource.Status.SUCCESS -> {
                        this.data?.let {
                            _isLoading.value = false

                            it.ifEmpty {
                                _isError.value = R.string.no_result
                            }
                            if(it.isNotEmpty()){
                                _movies.value = it
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        _isLoading.value = false
                        _isError.value = this.resId
                    }
                }
            }
        }
    }

    fun getMoviesByName(movieName: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                repository.getMovieByName(movieName)
            }.run {
                when (this.status) {
                    Resource.Status.SUCCESS -> {
                        this.data?.let {
                            _isLoading.value = false
                            it.ifEmpty {
                                _isError.value = R.string.no_result
                            }
                            if(it.isNotEmpty()){
                                _movies.value = it
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        _isLoading.value = false
                        _isError.value = this.resId
                    }
                }
            }
        }
    }

}