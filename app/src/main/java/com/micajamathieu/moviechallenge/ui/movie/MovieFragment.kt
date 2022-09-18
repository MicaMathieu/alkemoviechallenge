package com.micajamathieu.moviechallenge.ui.movie

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.micajamathieu.moviechallenge.R
import com.micajamathieu.moviechallenge.data.model.Movie
import com.micajamathieu.moviechallenge.databinding.FragmentMovieBinding
import com.micajamathieu.moviechallenge.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies()
        initRecyclerView()
        setupObservers()
        setupListener()
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.movie_menu,menu)

        val searchItem = menu.findItem(R.id.svMovie)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               if(!query.isNullOrEmpty()){
                   viewModel.getMoviesByName(query)
                   searchView.clearFocus()
               }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        //binding.rvMovies.layoutManager = GridLayoutManager(requireContext(),3)

    }

    private fun setupObservers() {
        viewModel.isError.observe(viewLifecycleOwner) { resId ->
            binding.cvMovie.isVisible = false
            binding.rvMovies.isVisible = false
            binding.tvError.isVisible = true
            binding.btnRetry.isVisible = true
            binding.tvError.text = getString(resId)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.cvMovie.isVisible = !it
            binding.rvMovies.isVisible = !it
            if(it){
                binding.tvError.isVisible = !it
                binding.btnRetry.isVisible = !it
            }
        }
        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            binding.rvMovies.adapter = MovieAdapter(movieList) { movie ->
                onMovieSelected(
                    movie
                )
            }
            val firstMovie = movieList.random()
            Glide.with(binding.imageView.context)
                .load(Constants.BASE_IMG_URL + firstMovie.posterPath)
                .error(R.drawable.ic_error)
                .into(binding.imageView)
            setupListenerOnMovie(firstMovie)
        }
    }


    private fun setupListenerOnMovie(movie: Movie) {
        binding.imageView.setOnClickListener {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(movie)
            findNavController().navigate(action)
        }
    }

    private fun setupListener(){
        binding.btnRetry.setOnClickListener {
            viewModel.getPopularMovies()
        }
    }

    private fun onMovieSelected(movie: Movie) {
        //Toast.makeText(requireContext(),movie.title,Toast.LENGTH_SHORT).show()
        //  findNavController().navigate(R.id.action_movieFragment_to_movieDetailsFragment)
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)

    }


}