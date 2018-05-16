package com.app.carparkregister

import android.R
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    var fragmentList = arrayListOf<Fragment>()
    var fragmentTitleList = arrayListOf<String>()

    fun addFragment(fragment: Fragment, fragmentTitle: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(fragmentTitle)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

}