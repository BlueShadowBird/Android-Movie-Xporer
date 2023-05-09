package id.web.dedekurniawan.moviexplorer.presentation.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.web.dedekurniawan.moviexplorer.AboutActivity
import id.web.dedekurniawan.moviexplorer.R
import id.web.dedekurniawan.moviexplorer.core.SettingsActivity
import id.web.dedekurniawan.moviexplorer.databinding.ActivitySearchBinding
import id.web.dedekurniawan.moviexplorer.core.adapter.MovieAdapter
import id.web.dedekurniawan.moviexplorer.core.utils.alert
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
            when(it){
                is id.web.dedekurniawan.moviexplorer.core.data.remote.Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is id.web.dedekurniawan.moviexplorer.core.data.remote.Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    movieAdapter.submitList(it.data)
                }
                is id.web.dedekurniawan.moviexplorer.core.data.remote.Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    alert(binding.root, TAG, it.message.toString())
                }
            }
        }

        installSplashScreen()
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_setting -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.app_bar_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

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

    companion object{
        private const val TAG = "SearchActivity"
    }
}