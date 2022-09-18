package com.micajamathieu.moviechallenge.ui.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.micajamathieu.moviechallenge.R
import com.micajamathieu.moviechallenge.data.model.Movie
import com.micajamathieu.moviechallenge.databinding.FragmentMovieBinding
import com.micajamathieu.moviechallenge.databinding.FragmentMovieDetailsBinding
import com.micajamathieu.moviechallenge.ui.movie.MovieViewModel
import com.micajamathieu.moviechallenge.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val arguments by navArgs<MovieDetailsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi(arguments.movie)
    }

    private fun setupUi(movie: Movie) {
        Glide.with(requireContext())
            .load(Constants.BASE_IMG_URL + movie.backdropPath)
            .error(R.drawable.ic_error)
            .into(binding.ivMovie)
        binding.tvlanguage.text = movie.originalLanguage.uppercase()
        binding.tvTittle.text = movie.title
        binding.tvAverage.text = movie.voteAverage.toString()
        binding.tvOverview.text = movie.overview
        binding.tvPopularity.text = movie.popularity.toString()
    }

}