package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.tab_fragment_three.*
import kotlinx.android.synthetic.main.tab_fragment_three.view.*

class TabFragmentThree : Fragment() {



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.tab_fragment_three, container, false)




        view.jip_button40.setOnClickListener {
            Toast.makeText(activity, "40 is now occupied!", Toast.LENGTH_SHORT).show()
            view.jip_button40.setText("Taken")
        }

        view.jip_button41.setOnClickListener {
            Toast.makeText(activity, "41 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        view.jip_button42.setOnClickListener {
            Toast.makeText(activity, "42 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        view.jip_button43.setOnClickListener {
            Toast.makeText(activity, "43 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        view.jip_button44.setOnClickListener {
            Toast.makeText(activity, "44 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        view.jip_button45.setOnClickListener {
            Toast.makeText(activity, "45 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        view.jip_button46.setOnClickListener {
            Toast.makeText(activity, "46 is now occupied!", Toast.LENGTH_SHORT).show()
        }

        return view
    }


}