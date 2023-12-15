package id.web.dedekurniawan.moviexplorer.app.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.get
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.mutableMapOf
import kotlin.collections.set

class MainActivity : AppMenuCompatActivity() {
    private val dashboardFragmentMap = mutableMapOf<Int, ModuleElement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moduleEngine = moduleEngineHandler.moduleEngine

        val bottomNavigation = binding.bottomNavigation

        if(moduleEngine.moduleElementList.size>1){
            val bottomNavigationMenu = bottomNavigation.menu
            moduleEngine.moduleElementList.values.forEach { moduleElement ->
                val dashboardModule = moduleElement.dashboardModule
                bottomNavigationMenu.add(Menu.NONE, dashboardModule.itemMenuId, Menu.NONE, moduleElement.name).setIcon(dashboardModule.itemMenuId)
                dashboardFragmentMap[dashboardModule.itemMenuId] = moduleElement
            }

            binding.bottomNavigation.setOnItemReselectedListener { menuItem ->
                moduleElement = dashboardFragmentMap[menuItem.itemId]!!
                replaceFragment(moduleElement.dashboardModule.createFragment())
            }

            bottomNavigation.selectedItemId = bottomNavigationMenu[0].itemId
        }else{
            bottomNavigation.visibility = View.GONE
            moduleElement = moduleEngine.moduleElementList.values.first()
            replaceFragment(moduleElement.dashboardModule.createFragment())
        }
    }
}