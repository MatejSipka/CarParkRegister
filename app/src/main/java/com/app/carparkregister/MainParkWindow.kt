package com.app.carparkregister

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_park_window.*
import android.net.ConnectivityManager

import android.widget.Toast
import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.UserDao
import com.app.carparkregister.domain.WeekDays
import com.app.carparkregister.service.ParkingReservationService
import com.app.carparkregister.utils.CommonUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainParkWindow : AppCompatActivity() {

    var sectionsPagerAdapter: SectionsPageAdapter? = null

    var parkService: ParkingReservationService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_park_window)

        parkService = ParkingReservationService(this, this@MainParkWindow)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (!CommonUtils().checkInternetConnection(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)) {
            Toast.makeText(this@MainParkWindow, "No internet connection!", Toast.LENGTH_LONG).show()
        }

        // SET TABS
        var viewPager: ViewPager = container
        setupViewPager(viewPager)
        main_tab_layout.setupWithViewPager(viewPager)

        parkService!!.handleTodayButtonHighlight()

        initialCarFetch()
        setCarsListener()
        setLotsListener()

        week_mon.setOnClickListener {
            parkService!!.handleWeekButtonsTextColor(week_mon)
            StoredData.instance.setDaySelected(WeekDays.MON)
        }
        week_tues.setOnClickListener {
            parkService!!.handleWeekButtonsTextColor(week_tues)
            StoredData.instance.setDaySelected(WeekDays.TUES)
        }
        week_wed.setOnClickListener {
            parkService!!.handleWeekButtonsTextColor(week_wed)
            StoredData.instance.setDaySelected(WeekDays.WED)
        }
        week_thurs.setOnClickListener {
            parkService!!.handleWeekButtonsTextColor(week_thurs)
            StoredData.instance.setDaySelected(WeekDays.THURS)
        }
        week_fri.setOnClickListener {
            parkService!!.handleWeekButtonsTextColor(week_fri)
            StoredData.instance.setDaySelected(WeekDays.FRI)
        }

    }

    fun setCarsListener() {
        var email = FirebaseAuth.getInstance().currentUser!!.email
        var database = FirebaseDatabase.getInstance()

        database.getReference("users/").orderByChild("email").equalTo(email).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var userDao = snapshot.children.first().getValue(UserDao::class.java)
                StoredData.instance.setUser(userDao!!)
                if (userDao?.cars == null) {
                    StoredData.instance.setStoredCars(ArrayList<CarDao>())
                } else {
                    StoredData.instance.setStoredCars(userDao?.cars!!)
                }
            }
        })
    }

    fun setLotsListener() {

        var database = FirebaseDatabase.getInstance()

        database.getReference("days/" + StoredData.instance.getDaySelected()).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var takenLots = snapshot.children
                for(lot:DataSnapshot in takenLots){



                }
            }
        })

    }

    fun initialCarFetch() {

        var email = FirebaseAuth.getInstance().currentUser!!.email
        var database = FirebaseDatabase.getInstance()

        database.getReference("users/").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var userDao = snapshot.children.first().getValue(UserDao::class.java)
                StoredData.instance.setUser(userDao!!)
                if (userDao?.cars == null) {
                    StoredData.instance.setStoredCars(ArrayList<CarDao>())
                } else {
                    StoredData.instance.setStoredCars(userDao?.cars!!)
                }
                parkService!!.updateCarsInUI(1, sectionsPagerAdapter!!.getItem(0).view!!, StoredData.instance.getStoredCars(), userDao)
                parkService!!.updateCarsInUI(2, sectionsPagerAdapter!!.getItem(1).view!!, StoredData.instance.getStoredCars(), userDao)
//                parkService!!.updateCarsInUI(3, sectionsPagerAdapter!!.getItem(2).view!!, StoredData.instance.getStoredCars(), userDao)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_park_window, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
        when (itemId) {
            R.id.logout -> {
                var instance = FirebaseAuth.getInstance()
                instance.signOut()
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
                finish()
            }
            R.id.garage -> {
                val intent = Intent(this, UserGarage::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun setupViewPager(viewPager: ViewPager) {

        sectionsPagerAdapter = SectionsPageAdapter(supportFragmentManager)
        sectionsPagerAdapter!!.addFragment(TabFragmentOne(), "GARÁŽ")
        sectionsPagerAdapter!!.addFragment(TabFragmentTwo(), "MEDIA HALL")
        sectionsPagerAdapter!!.addFragment(TabFragmentThree(), "JIP")
        viewPager.adapter = sectionsPagerAdapter
    }

}
