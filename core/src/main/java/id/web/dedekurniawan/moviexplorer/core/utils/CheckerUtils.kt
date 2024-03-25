package id.web.dedekurniawan.moviexplorer.core.utils

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
fun isInstalled(context: Context, packageName: String) = SplitInstallManagerFactory.create(context).installedModules.contains(packageName)
