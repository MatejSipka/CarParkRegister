package com.app.carparkregister.service

import android.content.Context
import com.app.carparkregister.R
import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.Button
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

    fun handleTodayButtonHighlight() {
        val day = CommonUtils().getCurrentDay()

        when (day) {
            Calendar.MONDAY -> {
                activity.findViewById<Button>(R.id.week_mon).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
            Calendar.TUESDAY -> {
                activity.findViewById<Button>(R.id.week_tues).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_tues).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
            Calendar.WEDNESDAY -> {
                activity.findViewById<Button>(R.id.week_wed).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_wed).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
            Calendar.THURSDAY -> {
                activity.findViewById<Button>(R.id.week_thurs).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_thurs).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
            Calendar.FRIDAY -> {
                activity.findViewById<Button>(R.id.week_fri).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_fri).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
            else -> {
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
            }
        }
    }

}