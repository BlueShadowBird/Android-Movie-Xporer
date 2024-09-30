package id.web.dedekurniawan.moviexplorer.presentation.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.web.dedekurniawan.moviexplorer.ModuleEngineHandler
import id.web.dedekurniawan.moviexplorer.R
import id.web.dedekurniawan.moviexplorer.core.presentation.view.ModuleListFragment
import id.web.dedekurniawan.moviexplorer.databinding.ActivityFavoriteBinding
import org.koin.android.ext.android.inject

class FavoriteActivity : AppCompatActivity() {
    private val moduleEngineHandler: ModuleEngineHandler by inject()
    private lateinit var moduleName: String
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        moduleName = intent.getStringExtra(
            EXTRA_MODULE_NAME
        )!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite $moduleName"

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = Bundle().apply {
            putBoolean(ModuleListFragment.ARGUMENT_ISFAVORITE, true)
        }

        val moduleElement = moduleEngineHandler.moduleEngine.moduleElementMap[moduleName]!!
        val fragment = moduleElement.createFragment()
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
    }
}