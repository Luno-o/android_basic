package com.example.materialdesign

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.materialdesign.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnNavigationItemSelectedListener() { item->
            when(item.itemId){
                R.id.page_1->{navController.navigate(R.id.repairFragment)

                    Snackbar.make(binding.root,"Соединение с сервером отсутствует, показаны сохранённые объекты",Snackbar.LENGTH_SHORT)
                        .setAnchorView(this.findViewById(R.id.bottom_navigation))
                        .setAction("Повторить"){
                            Snackbar.make(binding.root,"Список обновлен",Snackbar.LENGTH_SHORT)
                                .setAnchorView(this.findViewById(R.id.bottom_navigation)).show()
                        }
                        .show()
                    true}
                R.id.page_2->{
                    navController.navigate(R.id.editFragment)
                    true}
                R.id.page_3->{
                    navController.navigate(R.id.fortFragment)
                    true}
                else->false
            }
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val destId = controller.currentDestination?.id
            if (destId != null) {
                when (destId) {
                    R.id.repairFragment -> binding.bottomNavigation.menu.getItem(0).isChecked =
                        true
                    R.id.editFragment -> binding.bottomNavigation.menu.getItem(1).isChecked = true
                    R.id.fortFragment -> binding.bottomNavigation.menu.getItem(2).isChecked = true
                    else -> {}
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}