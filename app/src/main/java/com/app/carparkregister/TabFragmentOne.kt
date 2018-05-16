package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.carparkregister.service.ParkingReservationService

class TabFragmentOne : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var view: View = inflater!!.inflate(R.layout.tab_fragment_one, container, false)

        val prs = ParkingReservationService(activity, context)
        prs!!.updateCarsInUI(1, view)

        return view
    }


}