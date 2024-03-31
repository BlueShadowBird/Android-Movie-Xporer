package id.web.dedekurniawan.moviexplorer.core.presentation.view

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.databinding.ActivityInstallModuleBinding
import id.web.dedekurniawan.moviexplorer.core.domain.SettingModuleChangeHandler
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import org.koin.android.ext.android.inject
import java.io.IOException


class InstallModuleActivity : AppCompatActivity(){
    private lateinit var binding: ActivityInstallModuleBinding
    private lateinit var moduleName: String
    private lateinit var splitInstallManager: SplitInstallManager
    private var sessionId: Int? = null
    private val settingModuleChangeHandler by inject<SettingModuleChangeHandler>()
    private val listener = SplitInstallStateUpdatedListener{ state ->
        sessionId?.let {
            val lottieAnimationView = binding.lottieAnimationView//run only if install request success
            if (state.sessionId() == it) {
                when (state.status()) {
                    SplitInstallSessionStatus.UNKNOWN -> {
                        binding.installStatus.text = resources.getText(R.string.splitinstall_state_unknown)
                        lottieAnimationView.repeatCount = 0
                        lottieAnimationView.setAnimation(R.raw.lottie_fail)
                        lottieAnimationView.playAnimation()
                    }
                    SplitInstallSessionStatus.PENDING -> {
                        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                        lottieAnimationView.setAnimation(R.raw.lottie_init)
                        lottieAnimationView.playAnimation()


                        binding.installStatus.text = getString(R.string.splitinstall_state_pending)
                    }
                    SplitInstallSessionStatus.DOWNLOADING -> {
                        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                        lottieAnimationView.setMaxFrame(100)
                        lottieAnimationView.setAnimation(R.raw.lottie_download)
                        lottieAnimationView.playAnimation()
                        val progress = if(state.totalBytesToDownload()>0L)
                            state.bytesDownloaded().toFloat() / state.totalBytesToDownload().toFloat()
                        else
                            0F

                        binding.installStatus.text = String.format(getString(R.string.splitinstall_state_downloading), progress)
                    }
                    SplitInstallSessionStatus.DOWNLOADED -> {
                        lottieAnimationView.setMaxFrame(Integer.MAX_VALUE)
                        lottieAnimationView.repeatCount = 0

                        binding.installStatus.text = getString(R.string.splitinstall_state_downloaded)
                    }
                    SplitInstallSessionStatus.INSTALLING -> {
                        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                        lottieAnimationView.setAnimation(R.raw.lottie_loading)
                        lottieAnimationView.playAnimation()

                        binding.installStatus.text = getString(R.string.splitinstall_state_installing)
                    }
                    SplitInstallSessionStatus.INSTALLED -> {
                        lottieAnimationView.repeatCount = 0
                        lottieAnimationView.setAnimation(R.raw.lottie_done)

                        binding.installStatus.text = String.format(
                            getString(R.string.splitinstall_state_installed), moduleName
                        )
                        postInstalled()
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        lottieAnimationView.setAnimation(R.raw.lottie_fail)
                        lottieAnimationView.playAnimation()
                        binding.backButton.visibility = View.VISIBLE

                        binding.installStatus.text = getString(R.string.splitinstall_state_failed)
                    }
                    SplitInstallSessionStatus.CANCELING -> {
                        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                        lottieAnimationView.setAnimation(R.raw.lottie_loading)

                        binding.installStatus.text = getString(R.string.splitinstall_state_canceling)
                    }
                    SplitInstallSessionStatus.CANCELED -> {
                        binding.backButton.visibility = View.VISIBLE

                        binding.installStatus.text = getString(R.string.splitinstall_state_canceled)
                    }
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION ->                              // Large module that has size greater than 10 MB requires user permission
                        try {
                            splitInstallManager.startConfirmationDialogForResult(
                                state,
                                this, 110
                            )
                        } catch (ex: SendIntentException) {
                            lottieAnimationView.setAnimation(R.raw.lottie_fail)
                            binding.backButton.visibility = View.VISIBLE
                            binding.installStatus.text = getString(R.string.splitinstall_state_confirmation_fail)
                        }


                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moduleName = intent.getStringExtra(EXTRA_MODULE_NAME).toString()
        binding = ActivityInstallModuleBinding.inflate(layoutInflater)

        binding.backButton.setOnClickListener {
            finish()
        }

        setContentView(binding.root)

        splitInstallManager = SplitInstallManagerFactory.create(this)
        splitInstallManager.registerListener(listener)

        installModule()
    }

    private fun installModule() {
        if (!splitInstallManager.installedModules.contains(moduleName)){
            val request = SplitInstallRequest.newBuilder()
                .addModule(KEY_PERSON_MODULE)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener { sessionId ->
                    this.sessionId = sessionId
                }
                .addOnFailureListener { exception ->
                    when (exception) {
                        is SplitInstallException -> {
                            when (exception.errorCode) {
                                SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> {
                                    alert("Too many sessions are running for current app, existing sessions must be resolved first.")
                                }
                                else -> {
                                    alert("Error Install Feature")
                                }
                            }
                        }
                        is IOException -> {
                            alert("No Connection")
                        }
                        else -> {
                            alert("Unknown Error")
                        }
                    }
                }
        }else{
            alert("Module had been Installed")
            postInstalled()
            finish()
        }
    }

    private fun postInstalled(){
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_MODULE_NAME, moduleName)
        resultIntent.putExtra(EXTRA_INSTALL_STATUS, true)
        setResult(RESULT_OK, resultIntent)
        binding.backButton.visibility = View.VISIBLE

        settingModuleChangeHandler.notifyListener(moduleName)
    }

    private fun alert(message: String) {
        alert(this, TAG, message)
    }

    protected fun finalize(){
        alert("finalize class")
        splitInstallManager.unregisterListener(listener)
    }

    companion object{
        private const val TAG = "InstallModuleActivity"
        const val EXTRA_MODULE_NAME = "moduleName"
        const val EXTRA_INSTALL_STATUS = "installStatus"
    }
}