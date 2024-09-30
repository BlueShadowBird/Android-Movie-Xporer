package id.web.dedekurniawan.moviexplorer.core.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubeBinding
    private lateinit var youTubePlayer: YouTubePlayer
    private lateinit var videoList: List<YoutubeVideoData>
    private lateinit var  currentVideo: YoutubeVideoData
    private var currentVideoIdx = 0
    private var totalVideo = 0

    private var isControlVisible = false
    private var haveNext = false
    private var havePrevious = false
    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoList = intent.getStringArrayListExtra(EXTRA_ID_LIST)!!.map {
            YoutubeVideoData(it)
        }
        totalVideo = videoList.size
        currentVideo = videoList[currentVideoIdx]

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        val youTubePlayerView  = binding.youtubePlayerView

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(currentVideo.id, 0f)

                this@YoutubeActivity.youTubePlayer = youTubePlayer
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                currentVideo.second = second
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                when(state){
                    PlayerConstants.PlayerState.PLAYING -> {
                        toggleVisibility(false)
                    }
                    PlayerConstants.PlayerState.PAUSED -> {
                        toggleVisibility(true)
                    }
                    else -> {}
                }
            }
        })

        binding.run {
            closeButton.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            if(totalVideo > 1){
                haveNext = true
            }

            skipNextButton.setOnClickListener {
                currentVideo = videoList[++currentVideoIdx]
                if(totalVideo == currentVideoIdx+1){
                    haveNext = false
                }
                havePrevious = true
                youTubePlayer.loadVideo(currentVideo.id, currentVideo.second)
            }

            skipPreviousButton.setOnClickListener {
                currentVideo = videoList[--currentVideoIdx]
                if(currentVideoIdx == 0){
                    havePrevious = false
                }
                haveNext = true
                youTubePlayer.loadVideo(currentVideo.id, currentVideo.second)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun toggleVisibility(isVisible: Boolean = !isControlVisible) {
        isControlVisible = isVisible
        binding.run {
            if (isControlVisible) {
                if (haveNext) {
                    skipNextButton.startAnimation(fadeIn)
                    skipNextButton.visibility = View.VISIBLE
                }
                if (havePrevious) {
                    skipPreviousButton.startAnimation(fadeIn)
                    skipPreviousButton.visibility = View.VISIBLE
                }
            } else {
                if (haveNext) {
                    skipNextButton.startAnimation(fadeOut)
                    skipNextButton.visibility = View.GONE
                }
                if (havePrevious) {
                    skipPreviousButton.startAnimation(fadeOut)
                    skipPreviousButton.visibility = View.GONE
                }
            }
        }
    }

    companion object{
        const val EXTRA_ID_LIST = "idList"
    }

    data class YoutubeVideoData(val id: String, var second: Float = 0F)
}