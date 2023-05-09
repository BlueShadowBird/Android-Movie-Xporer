package id.web.dedekurniawan.moviexplorer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.web.dedekurniawan.moviexplorer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.privacyPolicyLink.setOnClickListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URL)))
    }
}