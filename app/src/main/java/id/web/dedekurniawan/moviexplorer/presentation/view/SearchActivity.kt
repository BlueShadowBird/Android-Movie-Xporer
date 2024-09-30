package id.web.dedekurniawan.moviexplorer.presentation.view

import android.os.Bundle
import android.view.MenuItem
import id.web.dedekurniawan.moviexplorer.core.presentation.view.ModuleListFragment

class SearchActivity: AppMenuCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        moduleElement = moduleEngineHandler.moduleEngine.moduleElementMap[intent.getStringExtra(EXTRA_MODULE_NAME)]!!

        val fragment = moduleElement.createFragment()
        val arguments = Bundle()
        arguments.putString(ModuleListFragment.ARGUMENT_SEARCH_QUERY, intent.getStringExtra(EXTRA_SEARCH_QUERY))
        fragment.arguments = arguments
        replaceFragment(fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object{
        const val EXTRA_MODULE_NAME = "extraModuleName"
        const val EXTRA_SEARCH_QUERY = "extraSearchQuery"
    }
}