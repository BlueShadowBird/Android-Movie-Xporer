package id.web.dedekurniawan.moviexplorer.movie.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.web.dedekurniawan.moviexplorer.core.presentation.view.ModuleListFragment
import id.web.dedekurniawan.moviexplorer.movie.adapter.MovieAdapter
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.presentation.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : ModuleListFragment<Movie>() {
    private val movieAdapter: MovieAdapter by inject()
    override val viewModel: MovieViewModel by viewModel()

    override fun processData(dataList: List<Movie>?) {
        movieAdapter.submitList(dataList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.rvSearch.adapter = movieAdapter
        movieAdapter.onClickListener = { movie ->
            val intent = Intent(requireContext(), MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
            startActivity(intent)
        }

        return view
    }

    companion object{
        private const val TAG = "MovieListFragment"
    }
}