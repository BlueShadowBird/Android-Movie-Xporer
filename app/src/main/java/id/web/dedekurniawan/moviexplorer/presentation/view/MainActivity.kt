package id.web.dedekurniawan.moviexplorer.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.get
import id.web.dedekurniawan.moviexplorer.ModuleEngineHandler
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.mutableMapOf
import kotlin.collections.set

class MainActivity : AppMenuCompatActivity(), ModuleEngineHandler.ModuleEngineUpdateListener {
    private val dashboardFragmentMap = mutableMapOf<Int, ModuleElement>()
    private var needUpdateBottomNavigation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moduleEngineHandler.addModuleEngineUpdateListener(this)
    }

    override fun onResume() {
        super.onResume()
        if(needUpdateBottomNavigation){
            val moduleEngine = moduleEngineHandler.moduleEngine

            val bottomNavigation = binding.bottomNavigation

            if(moduleEngine.moduleElementMap.size>1){
                val bottomNavigationMenu = bottomNavigation.menu
                bottomNavigationMenu.clear()
                moduleEngine.moduleElementMap.values.forEach { moduleElement ->
                    bottomNavigationMenu.add(Menu.NONE, moduleElement.itemMenuId, Menu.NONE, moduleElement.name).setIcon(moduleElement.itemMenuId)
                    dashboardFragmentMap[moduleElement.itemMenuId] = moduleElement
                }

                binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
                    moduleElement = dashboardFragmentMap[menuItem.itemId]!!
                    replaceFragment(moduleElement.createFragment())
                    return@setOnItemSelectedListener true
                }

                bottomNavigation.selectedItemId = bottomNavigationMenu[0].itemId
                bottomNavigation.visibility = View.VISIBLE
            }else{
                moduleElement = moduleEngine.moduleElementMap.values.first()
                replaceFragment(moduleElement.createFragment())
                bottomNavigation.visibility = View.GONE
            }

            needUpdateBottomNavigation = false
        }
    }

    override fun onDestroy() {
        moduleEngineHandler.removeModuleEngineUpdateListener(this)
        super.onDestroy()
    }

    override fun moduleEngineUpdated() {
        needUpdateBottomNavigation = true
    }
}