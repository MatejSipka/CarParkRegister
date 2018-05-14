package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.tab_fragment_one.*

import kotlinx.android.synthetic.main.tab_fragment_one.view.*

class TabFragmentOne : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.tab_fragment_one, container, false)

//        view.findViewById<Button>(R.id.gar_13).startAnimation(
//                AnimationUtils.loadAnimation(activity, R.anim.button_rotation)
//        )

        return view
    }


}