package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.carparkregister.service.ParkingReservationService
import kotlinx.android.synthetic.main.b_tab_fragment_two.view.*

class TabFragmentTwo : Fragment() {

    var prs: ParkingReservationService? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.b_tab_fragment_two, container, false)

        prs = ParkingReservationService(activity, context)
        prs!!.updateCarsInUI(2, view, prs!!.getStoredCars())

        return view
    }


}