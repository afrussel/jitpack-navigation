package com.w3engineers.jitpackbottomnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.w3engineers.jitpackbottomnav.util.AnimUtil


class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {


    private lateinit var navController: NavController
    private lateinit var navigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation = findViewById(R.id.bottom_nav) as BottomNavigationView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.shop_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(navigation, navHostFragment.navController)

        navController = navHostFragment.navController

        navController.addOnNavigatedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        val title = destination.label
        supportActionBar!!.title = title
        if(title!!.equals("Profile") || title!!.equals("Profile image")){
            toggleBottomView(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toggleBottomView(true)
        }
    }


    private fun toggleBottomView(needToShow: Boolean) {
        if(needToShow){
            AnimUtil.slideUp(this,navigation)
        }else{
            AnimUtil.slideDown(this,navigation)
        }
    }

}
