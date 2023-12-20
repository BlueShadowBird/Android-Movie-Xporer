package id.web.dedekurniawan.moviexplorer.presentation.view

import android.os.Bundle
import id.web.dedekurniawan.moviexplorer.core.presentation.view.ModuleListFragment

class SearchActivity: AppMenuCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moduleElement = moduleEngineHandler.moduleEngine.moduleElementList[intent.getStringExtra(EXTRA_MODULE_NAME)]!!

        val fragment = moduleElement.createFragment()
        val arguments = Bundle()
        arguments.putString(ModuleListFragment.SEARCH_QUERY_ARGUMENT, intent.getStringExtra(EXTRA_SEARCH_QUERY))
        fragment.arguments = arguments
        replaceFragment(fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object{
        const val EXTRA_MODULE_NAME = "extraModuleName"
        const val EXTRA_SEARCH_QUERY = "extraSearchQuery"
    }
}