package com.example.nasa

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.nasa.apod.ApodFragment
import com.example.nasa.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        toolbar.title = "APOD"
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, Drawer, toolbar,R.string.open,R.string.close)
        toggle.drawerArrowDrawable.color = Color.WHITE
        Drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_layout, ApodFragment())
            .commit()
        NavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.apod -> {
                    //supportFragmentManager.beginTransaction().remove(supportFragmentManager.fragments[0])
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,
                        ApodFragment()
                    ).commit()
                    SearchFilter.visibility = View.GONE
                    toolbar.title = "APOD"

                    Drawer.closeDrawers()
                    true
                }
                R.id.search -> {
                    SearchFilter.setQuery("",false)
                    SearchFilter.isIconified = true
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_layout,
                        SearchFragment()
                    ).commit()
                    toolbar.title = "Search"

                    SearchFilter.visibility = View.VISIBLE

                    Drawer.closeDrawers()
                    true
                }

                else -> false
            }

        }
    }
}