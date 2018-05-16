package com.app.carparkregister.service

import android.content.Context
import com.app.carparkregister.R
import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.ParkingLot
import com.app.carparkregister.domain.UserDao
import com.app.carparkregister.domain.WeekDays
import com.app.carparkregister.utils.CommonUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar


class ParkingReservationService(activity: Activity, context: Context) {

    private var activity: Activity = activity
    private var context: Context = context

    private var selectetDay: WeekDays? = null

    public fun setSelectedDay(day: WeekDays) {
        this.selectetDay = day
    }

    fun handleWeekButtonsTextColor(clicked: Button) {
        activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day))
        activity.findViewById<Button>(R.id.week_tues).setTextColor(ContextCompat.getColor(context, R.color.day))
        activity.findViewById<Button>(R.id.week_wed).setTextColor(ContextCompat.getColor(context, R.color.day))
        activity.findViewById<Button>(R.id.week_thurs).setTextColor(ContextCompat.getColor(context, R.color.day))
        activity.findViewById<Button>(R.id.week_fri).setTextColor(ContextCompat.getColor(context, R.color.day))
        clicked.setTextColor(ContextCompat.getColor(context, R.color.day_selected))
    }

    fun fetchLotsForSelectedDay(day: WeekDays) {

        var database = FirebaseDatabase.getInstance()

        database.getReference("days/").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                when (day) {
                    WeekDays.MON -> {
//                        for(i in 1..13) {
//                            val resID = activity.getResources().getIdentifier("GAR" + i.toString(), "id", activity.getPackageName())
//                            activity.findViewById<Button>(resID).text = "FREE"
//                            activity.findViewById<Button>(resID).setTextColor(ContextCompat.getColor(context, R.color.free_parking_lot))
//                        }
                    }
                }

            }
        })
    }

    fun prepareLotObject(user: UserDao, car: CarDao, lotID: String): ParkingLot {
        var lot = ParkingLot()
        lot.takenBy = user.copy()
        lot.lotId = lotID
        lot.carParked = car
        return lot
    }

    fun updateCarsInUI(whichFragmentTab: Int, view: View, storedCars: ArrayList<CarDao>, user: UserDao?) {

        var lotsNumber = 0
        var lotsId = ""
        if (whichFragmentTab == 1) {
            lotsNumber = 13
            lotsId = "GAR"
        } else if (whichFragmentTab == 2) {
            lotsNumber = 12
            lotsId = "MH"
        } else {
            lotsNumber = 8
            lotsId = "JIP"
        }

        for (i in 1..lotsNumber) {
            val resID = context.getResources().getIdentifier(lotsId + i, "id", context.packageName)
            view.findViewById<Button>(resID).setOnClickListener {
                if (storedCars.size > 1) {
                    val builderSingle = AlertDialog.Builder(context)
                    builderSingle.setTitle("Select Your Car")

                    val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item)
                    for (car: CarDao in storedCars) {
                        arrayAdapter.add(car.color + " " + car.model + " - " + car.spz)
                    }

                    builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                    builderSingle.setAdapter(arrayAdapter) { dialog, which ->

                        sendReservationToDB(prepareLotObject(user!!, storedCars.get(which), lotsId + i))
                    }
                    builderSingle.show()
                } else if (storedCars.size == 1) {
                    sendReservationToDB(prepareLotObject(user!!, storedCars.get(0), lotsId + i))
                } else {
                    Toast.makeText(context, "You don't have any car.", Toast.LENGTH_LONG)
                            .show()
                }
            }
        }

    }

    fun sendReservationToDB(lotObject: ParkingLot) {

        // REMOVE UNNECESSARY CARS FROM USER
        lotObject.takenBy!!.cars = null

        var reference = "days/" + StoredData.instance.getDaySelected()
        var database = FirebaseDatabase.getInstance()
        val ref = database.getReference(reference)
        val key = lotObject.lotId
        ref.child(key).setValue(lotObject)

    }

    fun handleTodayButtonHighlight() {
        val day = CommonUtils().getCurrentDay()

        when (day) {
            Calendar.MONDAY -> {
                activity.findViewById<Button>(R.id.week_mon).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.MON)
            }
            Calendar.TUESDAY -> {
                activity.findViewById<Button>(R.id.week_tues).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_tues).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.TUES)
            }
            Calendar.WEDNESDAY -> {
                activity.findViewById<Button>(R.id.week_wed).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_wed).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.WED)
            }
            Calendar.THURSDAY -> {
                activity.findViewById<Button>(R.id.week_thurs).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_thurs).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.THURS)
            }
            Calendar.FRIDAY -> {
                activity.findViewById<Button>(R.id.week_fri).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_fri).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.FRI)
            }
            else -> {
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                StoredData.instance.setDaySelected(WeekDays.MON)
            }
        }
    }

}