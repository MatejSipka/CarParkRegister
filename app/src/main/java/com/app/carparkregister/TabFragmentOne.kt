package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.tab_fragment_one.view.*

class TabFragmentOne : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.tab_fragment_one, container, false)

        view.button4.setOnClickListener {
            Toast.makeText(activity, "aaaaa", Toast.LENGTH_LONG).show()
        }

        return view
    }


}