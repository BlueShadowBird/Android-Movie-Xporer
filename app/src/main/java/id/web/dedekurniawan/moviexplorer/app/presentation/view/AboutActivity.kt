package id.web.dedekurniawan.moviexplorer.app.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.web.dedekurniawan.moviexplorer.app.BuildConfig
import id.web.dedekurniawan.moviexplorer.app.R
import id.web.dedekurniawan.moviexplorer.app.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spannableString = SpannableString(getString(R.string.read_privacy_policy))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URL)))
            }
        }
        val start = spannableString.indexOf(getString(R.string.clickable_privacy_policy_text))
        val length = getString(R.string.clickable_privacy_policy_text).length
        spannableString.setSpan(clickableSpan, start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.readPrivacyPolicy.text = spannableString
        binding.readPrivacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        binding.backAbout.setOnClickListener(this)

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.back_about -> finish()
        }
    }
}