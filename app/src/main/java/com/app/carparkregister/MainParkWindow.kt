package com.app.carparkregister

import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.view.ViewPager
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


    fun setupViewPager(viewPager:ViewPager){

        var adapter:SectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(TabFragmentOne(), "GARAZ")
        adapter.addFragment(TabFragmentTwo(), "MEDIA HALL")
        adapter.addFragment(TabFragmentThree(), "JIP")
        viewPager.adapter = adapter
    }

}
