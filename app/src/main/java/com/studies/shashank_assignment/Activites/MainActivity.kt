package com.studies.shashank_assignment.Activites

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.studies.shashank_assignment.Fragments.DocsFragment
import com.studies.shashank_assignment.Fragments.GameFragment
import com.studies.shashank_assignment.Fragments.HomeFragment
import com.studies.shashank_assignment.Fragments.PaperFragment
import com.studies.shashank_assignment.Fragments.SettingFragment
import com.studies.shashank_assignment.R

class MainActivity : AppCompatActivity(){
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { view, insets ->
//            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            view.setPadding(0, systemBarsInsets.top, 0, 0)
//            insets
//        }
        val rootView: View = findViewById(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply padding only on the right side
            v.setPadding(v.paddingLeft, v.paddingTop, systemBars.right, v.paddingBottom)

            // Return the insets with the consumed system bars
            insets.consumeSystemWindowInsets()
        }
        // Hide the status bar
        actionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN



        val toolbar: Toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        // Set default selected item
        navView.setCheckedItem(R.id.nav_docs)
        loadFragment(DocsFragment())

        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_game -> loadFragment(GameFragment())
                R.id.nav_docs -> loadFragment(DocsFragment())
                R.id.nav_sheet -> loadFragment(PaperFragment())
                R.id.nav_settings -> loadFragment(SettingFragment())
            }

            // Close the navigation drawer after selecting an item
            drawerLayout.closeDrawer(GravityCompat.START)

            true // Return true to indicate the item is selected
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
