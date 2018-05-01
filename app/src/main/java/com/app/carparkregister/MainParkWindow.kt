package com.app.carparkregister

import android.content.Intent
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_park_window.*

class MainParkWindow : AppCompatActivity() {

    var sectionsPagerAdapter: SectionsPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_park_window)

        // SET TABS
        sectionsPagerAdapter = SectionsPageAdapter(supportFragmentManager)
        var viewPager:ViewPager = container
        setupViewPager(viewPager)
        main_tab_layout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_park_window, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
            when(itemId){
                R.id.logout -> {
                    var instance = FirebaseAuth.getInstance()
                    instance.signOut()
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.garage -> {

                }
            }


        return super.onOptionsItemSelected(item)
    }

    fun setupViewPager(viewPager:ViewPager){

        var adapter:SectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(TabFragmentOne(), "GARÁŽ")
        adapter.addFragment(TabFragmentTwo(), "MEDIA HALL")
        adapter.addFragment(TabFragmentThree(), "JIP")
        viewPager.adapter = adapter
    }

}
