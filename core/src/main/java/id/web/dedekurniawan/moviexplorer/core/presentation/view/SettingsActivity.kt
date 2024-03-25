package id.web.dedekurniawan.moviexplorer.core.presentation.view

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_INCLUDE_ADULT
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_THEME
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import id.web.dedekurniawan.moviexplorer.core.utils.changeTheme


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_frame, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
        private lateinit var includeAdultSwitch: SwitchPreferenceCompat
        private lateinit var personModuleSwitch: SwitchPreferenceCompat

        private val installModuleActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if(result.resultCode == RESULT_OK){
                val data: Intent? = result.data
                when(data?.getStringExtra(InstallModuleActivity.EXTRA_MODULE_NAME).toString()){
                    KEY_PERSON_MODULE -> {
                        personModuleSwitch.isChecked =
                            data?.getBooleanExtra(InstallModuleActivity.EXTRA_INSTALL_STATUS, false) == true
                    }
                }
            }
        }
        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
            super.onPause()
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            init()
        }

        private fun init() {
            includeAdultSwitch = findPreference(KEY_INCLUDE_ADULT)!!
            includeAdultSwitch.setOnPreferenceChangeListener { preference, newValue ->
                if(newValue == true){
                    val dialogClickListener: DialogInterface.OnClickListener =
                        DialogInterface.OnClickListener { _, which ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    val biometricManager = BiometricManager.from(requireContext())
                                    var promptInfo: PromptInfo? = null

                                    when (BiometricManager.BIOMETRIC_SUCCESS) {
                                        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) -> {
                                            promptInfo = PromptInfo.Builder()
                                                .setTitle("Authentication required")
                                                .setSubtitle("Please authenticate to continue")
                                                .setDescription("Please use your biometric credentials to authenticate.")
                                                .setNegativeButtonText("Cancel")
                                                .build()
                                        }
                                        biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) -> {
                                            promptInfo = PromptInfo.Builder()
                                                .setTitle("Unlock your device")
                                                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                                                .build()
                                        }
                                        else -> {
                                            alert("Security device is required to be set up to include adult content")
                                        }
                                    }

                                    if (promptInfo != null){
                                        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()),
                                            object : BiometricPrompt.AuthenticationCallback() {
                                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                                    (preference as SwitchPreferenceCompat).isChecked = true
                                                }

                                                override fun onAuthenticationFailed() {
                                                    alert("Authentication Failed")
                                                }
                                            })

                                        biometricPrompt.authenticate(promptInfo)
                                    }
                                }
                            }
                        }

                    AlertDialog.Builder(requireContext())
                        .setPositiveButton(getString(R.string.include_adult_yes), dialogClickListener)
                        .setNegativeButton(getString(R.string.include_adult_no), dialogClickListener)
                        .setView(requireActivity().layoutInflater.inflate(R.layout.adult_only_dialog, view as ViewGroup, false))
                        .show()

                    return@setOnPreferenceChangeListener false  // include adult is checked, change to true only On Authentication Succeeded
                }

                return@setOnPreferenceChangeListener true   // include adult is unchecked, update new value
            }

            personModuleSwitch = findPreference(KEY_PERSON_MODULE)!!
            personModuleSwitch.setOnPreferenceChangeListener { preference, newValue ->
                if(newValue == true){
                    val dialogClickListener: DialogInterface.OnClickListener =
                        DialogInterface.OnClickListener { _, which ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    val intent = Intent(requireActivity(), InstallModuleActivity::class.java)
                                    intent.putExtra(InstallModuleActivity.EXTRA_MODULE_NAME, KEY_PERSON_MODULE)
                                    installModuleActivityResultLauncher.launch(intent)
                                }
                            }
                        }

                    AlertDialog.Builder(requireContext())
                        .setPositiveButton(getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getString(R.string.no), dialogClickListener)
                        .setMessage(R.string.person_module_dialog_message)
                        .show()

                    return@setOnPreferenceChangeListener false  // person is checked, change to true only On Authentication Succeeded
                }

                return@setOnPreferenceChangeListener false   // person is unchecked, update new value
            }
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            when(key){
                KEY_THEME ->
                    changeTheme(sharedPreferences?.getString(KEY_THEME, null)?.toInt())
            }
        }

        private fun alert(message: String) {
            alert(this.requireContext(), TAG, message)
        }

        companion object{
            private const val TAG = "SettingsActivity"
        }
    }
}