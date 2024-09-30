package id.web.dedekurniawan.moviexplorer.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_INCLUDE_ADULT

class SettingRepository(
    context: Context
): ISettingRepository {
    private val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getIncludeAdultSetting() = sharedPref.getBoolean(KEY_INCLUDE_ADULT, false)
}