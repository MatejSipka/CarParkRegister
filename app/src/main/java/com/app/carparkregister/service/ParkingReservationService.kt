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
import com.app.carparkregister.utils.CommonUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar
import com.google.gson.Gson
import android.content.Context.MODE_PRIVATE
import com.app.carparkregister.domain.*
import com.google.gson.reflect.TypeToken


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

    fun prepareLotObject(user: UserDao, car: CarDao, lotID: String): ParkingLot {
        var lot = ParkingLot()
        lot.takenBy = user.copy()
        lot.lotId = lotID
        lot.carParked = car
        return lot
    }

    fun updateCarsInUI(whichFragmentTab: Int, view: View) {

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

        val mPrefs = activity.getPreferences(MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("storedCars", "")
        val type = object : TypeToken<ArrayList<CarDao>>() {}.type
        val storedCars = gson.fromJson<ArrayList<CarDao>>(json, type)
        val jsonUser = mPrefs.getString("user", "")
        val user = gson.fromJson(jsonUser, UserDao::class.java)
        val selectedDayjson = mPrefs.getString("selectedDay", "")
        val selectedDay = gson.fromJson(selectedDayjson, WeekDays::class.java)
        val storedReservationDayjson = mPrefs.getString("storedReservationDay$selectedDay", "")
        val storedReservationDay = gson.fromJson(storedReservationDayjson, DayDto::class.java)

        var curentLotCar: CarDao? = null
        var curentLotUsr: UserDao? = null

        for (i in 1..lotsNumber) {
            val resID = context.getResources().getIdentifier(lotsId + i, "id", context.packageName)
            var lotButton = view.findViewById<Button>(resID)

            if (storedReservationDay != null) {

                lotButton.text = "Free"
                lotButton.setTextColor(ContextCompat.getColor(context, R.color.free_parking_lot))

                var uIlotID = lotsId + i
                var lotsForDay = storedReservationDay.lots
                for (lot in lotsForDay!!) {
                    if (lot.lotId.equals(uIlotID)) {
                        curentLotUsr = lot.takenBy
                        curentLotCar = lot.carParked
                        lotButton.text = lot.carParked?.spz
                        lotButton.setTextColor(ContextCompat.getColor(context, R.color.day_selected))

                    }
                }
            }

            lotButton.setOnClickListener {

                if (lotButton.text.equals("Free")) {
                    if (storedCars.size > 1) {
                        val builderSingle = AlertDialog.Builder(context)
                        builderSingle.setTitle("Select Your Car")

                        val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item)
                        for (car: CarDao in storedCars) {
                            arrayAdapter.add(car.toString())
                        }

                        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                        builderSingle.setAdapter(arrayAdapter) { dialog, which ->

                            sendReservationToDB(prepareLotObject(user!!, storedCars.get(which), lotsId + i))
                        }
                        builderSingle.show()
                    } else if (storedCars.size == 1) {

                        var car = storedCars.get(0)
                        if (car.model.isBlank() && car.model.isBlank()) {
                            Toast.makeText(context, "You don't have any car.", Toast.LENGTH_LONG)
                                    .show()
                        } else {
                            sendReservationToDB(prepareLotObject(user!!, storedCars.get(0),
                                    lotsId + i))
                        }
                    }
                } else {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setTitle("Lot is taken.")
                    alertBuilder.setMessage("By colleague: " + curentLotUsr.toString()
                            + "\n"
                            + "\n"
                            + "Car: " + curentLotCar.toString())
                    alertBuilder.setNegativeButton("ok") { dialog, which -> dialog.dismiss() }
                    alertBuilder.show()
                }

            }
        }

    }

    fun sendReservationToDB(lotObject: ParkingLot) {

        // REMOVE UNNECESSARY CARS FROM USER
        lotObject.takenBy!!.cars = null

        val mPrefs = activity.getPreferences(MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("selectedDay", "")
        val selectedDay = gson.fromJson(json, WeekDays::class.java)


        var reference = "days/" + selectedDay
        var database = FirebaseDatabase.getInstance()
        val ref = database.getReference(reference)
        val key = lotObject.lotId
        ref.child(key).setValue(lotObject)

    }

    fun handleTodayButtonHighlight() {
        val day = CommonUtils().getCurrentDay()

        val mPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()

        when (day) {
            Calendar.MONDAY -> {
                activity.findViewById<Button>(R.id.week_mon).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.MON)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
            Calendar.TUESDAY -> {
                activity.findViewById<Button>(R.id.week_tues).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_tues).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.TUES)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
            Calendar.WEDNESDAY -> {
                activity.findViewById<Button>(R.id.week_wed).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_wed).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.WED)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
            Calendar.THURSDAY -> {
                activity.findViewById<Button>(R.id.week_thurs).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_thurs).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.THURS)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
            Calendar.FRIDAY -> {
                activity.findViewById<Button>(R.id.week_fri).setBackground(ContextCompat.getDrawable(context, R.drawable.weeks_button_today))
                activity.findViewById<Button>(R.id.week_fri).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.FRI)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
            else -> {
                activity.findViewById<Button>(R.id.week_mon).setTextColor(ContextCompat.getColor(context, R.color.day_selected))
                val selectedDay = gson.toJson(WeekDays.MON)
                prefsEditor.putString("selectedDay", selectedDay)
                prefsEditor.commit()
            }
        }
    }

}