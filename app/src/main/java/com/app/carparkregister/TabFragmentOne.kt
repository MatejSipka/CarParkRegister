package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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

//        for(i in 1..13){
//            val resID = activity.getResources().getIdentifier("GAR"+i.toString(), "id", activity.getPackageName())
//            view.findViewById<Button>(R.id.GAR2).text = "FREE"
//            view.findViewById<Button>(R.id.GAR2).setTextColor(ContextCompat.getColor(context, R.color.free_parking_lot))
//        }

        return view
    }


}