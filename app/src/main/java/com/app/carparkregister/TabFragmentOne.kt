package com.app.carparkregister

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.service.ParkingReservationService
import kotlinx.android.synthetic.main.tab_fragment_one.*

import kotlinx.android.synthetic.main.tab_fragment_one.view.*

class TabFragmentOne : Fragment() {

    var prs: ParkingReservationService? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater!!.inflate(R.layout.tab_fragment_one, container, false)

        prs = ParkingReservationService(activity, context)
        prs!!.updateCarsInUI(1, view, prs!!.getStoredCars())

        return view
    }


}