package id.web.dedekurniawan.moviexplorer.presentation.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import id.web.dedekurniawan.moviexplorer.R
import id.web.dedekurniawan.moviexplorer.databinding.ActivitySearchBinding
import id.web.dedekurniawan.moviexplorer.core.adapter.MovieAdapter
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val movieAdapter: MovieAdapter by inject()
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        binding.rvSearch.adapter = movieAdapter
        movieAdapter.onClickListener = { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movie.id)
            startActivity(intent)
        }

        viewModel.searchResult.observe(this){
            if (it is id.web.dedekurniawan.moviexplorer.core.data.remote.Result.Success){
                movieAdapter.submitList(it.data)
            }
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
}