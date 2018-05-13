package com.app.carparkregister.service

import android.content.Context
import com.app.carparkregister.R
import java.util.*
import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.Button


class ParkingReservationService(activity: Activity, context: Context) {

    private var activity: Activity = activity
    private var context:Context = context

    fun handleTodayButtonHighlight() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        when (day) {
            Calendar.MONDAY -> {
                activity.findViewById<Button>(R.id.week_mon).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
            Calendar.TUESDAY -> {
                activity.findViewById<Button>(R.id.week_tues).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
            Calendar.WEDNESDAY -> {
                activity.findViewById<Button>(R.id.week_wed).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
            Calendar.THURSDAY -> {
                activity.findViewById<Button>(R.id.week_thurs).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
            Calendar.FRIDAY -> {
                activity.findViewById<Button>(R.id.week_fri).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
            else -> {
                activity.findViewById<Button>(R.id.week_mon).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
            }
        }
    }

}