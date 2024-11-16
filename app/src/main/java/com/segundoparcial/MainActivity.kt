package com.segundoparcial

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.segundoparcial.adapter.TabPageAdapter
import com.segundoparcial.view.HomeFragment
import com.segundoparcial.view.ListFragment
import com.segundoparcial.view.RegisterFragment

class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout : DrawerLayout
    lateinit var navView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initComponet()
        initListener()
        initUi(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponet() {
        drawerLayout =  findViewById(R.id.main)
        navView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open ,R.string.close)
    }

    private fun initListener(){
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fgContainer, HomeFragment())
                    .addToBackStack(null)
                    .commit()

                R.id.nav_Register -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fgContainer, RegisterFragment())
                    .addToBackStack(null)
                    .commit()

                R.id.nav_lista -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fgContainer, ListFragment())
                    .addToBackStack(null).commit()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private  fun initUi(savedInstanceState : Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fgContainer, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}