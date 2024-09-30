package id.web.dedekurniawan.moviexplorer.core.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import id.web.dedekurniawan.moviexplorer.core.adapter.MediaCarouselAdapter
import id.web.dedekurniawan.moviexplorer.core.databinding.ActivityCarouselMediaBinding
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo

class CarouselMediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarouselMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()

        binding = ActivityCarouselMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mediaList = intent.getParcelableArrayListExtra<MediaSourceInfo>(MEDIA_LIST_EXTRA)

        binding.run {
            carouselViewPager.adapter = mediaList?.let { MediaCarouselAdapter(it) }

            TabLayoutMediator(tabLayout, carouselViewPager) { _, _ ->
            }.attach()

            closeButton.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            carouselViewPager.adapter
        }
    }
    companion object{
        const val MEDIA_LIST_EXTRA = "mediaList"
    }
}