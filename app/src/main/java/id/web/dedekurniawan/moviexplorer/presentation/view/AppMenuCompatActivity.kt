package id.web.dedekurniawan.moviexplorer.presentation.view

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import id.web.dedekurniawan.moviexplorer.ModuleEngineHandler
import id.web.dedekurniawan.moviexplorer.R
import id.web.dedekurniawan.moviexplorer.core.SettingsActivity
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.databinding.FrameContainerBinding
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.QuicksearchVIewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class AppMenuCompatActivity : AppCompatActivity() {
    protected lateinit var binding: FrameContainerBinding
    protected val moduleEngineHandler: ModuleEngineHandler by inject()
    protected lateinit var moduleElement: ModuleElement
    private val quicksearchVIewModel: QuicksearchVIewModel by viewModel()
    private lateinit var suggestionAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = FrameContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun quickSearch(query: String) {
        quicksearchVIewModel.quickSearch(moduleElement.name, query)
    }

    fun search(query: String) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(SearchActivity.EXTRA_MODULE_NAME, moduleElement.name)
        intent.putExtra(SearchActivity.EXTRA_SEARCH_QUERY, query)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        val suggestionColumns = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val suggestionItemLayout = android.R.layout.simple_dropdown_item_1line
        suggestionAdapter = SimpleCursorAdapter(
            this,
            suggestionItemLayout,
            null,
            suggestionColumns,
            intArrayOf(android.R.id.text1),
            0
        )
        searchView.suggestionsAdapter = suggestionAdapter

        suggestionAdapter.viewBinder = object: SimpleCursorAdapter.ViewBinder{
            override fun setViewValue(view: View?, cursor: Cursor?, columnIndex: Int): Boolean {
                if (view?.id == id.web.dedekurniawan.moviexplorer.core.R.id.quick_search_icon) {
                    val imageView =view as ImageView
                    val resID = applicationContext.resources.getIdentifier(
                        cursor?.getString(columnIndex),
                        "drawable",
                        applicationContext.packageName
                    )
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, resID, null))
                    return true
                }
                return false
            }
        }

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = suggestionAdapter.cursor
                cursor.moveToPosition(position)
                val query = cursor.getString(cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(query, true)
                return true
            }
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                searchView.isIconified = true
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                quickSearch(newText)
                return true
            }
        })

        quicksearchVIewModel.quickSearchResult.observe(this){ result ->
            when(result){
                is Result.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {

                    val cursor = MatrixCursor(arrayOf("_id", SearchManager.SUGGEST_COLUMN_TEXT_1))

                    // Add data to the cursor
                    result.data?.forEachIndexed { index, item ->
                        // Create a row in the cursor with the data
                        cursor.addRow(arrayOf(index, item.name))
                    }

                    // Close the cursor when done
                    cursor.close()
                    suggestionAdapter.changeCursor(cursor)
                }
                is Result.Error -> {
//                    alert(binding.root, result.message.toString())
                }
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_setting -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.app_bar_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return true
    }

    protected fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}