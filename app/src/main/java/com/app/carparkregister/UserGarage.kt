package com.app.carparkregister

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter

import android.widget.ExpandableListView
import android.widget.Toast
import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserGarage : AppCompatActivity() {

    var listAdapter: ExpandableListAdapter? = null
    var expListView: ExpandableListView? = null
    var listDataHeader: ArrayList<String> = arrayListOf()
    var listDataChild: HashMap<String, List<String>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_garage)

        val toolbar = findViewById<Toolbar>(R.id.garage_toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        var storedCars: ArrayList<CarDao>? = null
        // USER SHOULD NOT BE NULL SINCE HE IS LOGGED IN
        var email = FirebaseAuth.getInstance().currentUser!!.email
        var database = FirebaseDatabase.getInstance()

        database.getReference("users/").orderByChild("email").equalTo(email).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var userDao = snapshot.children.first().getValue(UserDao::class.java)
                storedCars = userDao?.cars

                expListView = findViewById<View>(R.id.expandable_list_garage) as ExpandableListView?
                prepareListData(storedCars)
                listAdapter = ExpandableListAdapter(this@UserGarage, listDataHeader as List<String>, listDataChild)
                expListView?.setAdapter(listAdapter)

            }
        })
    }

    fun goBack() {
        val intent = Intent(this, MainParkWindow::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_garage_window, menu)
        return true
    }

    // TODO MOVE TO ONE CLASS
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
        when (itemId) {
            R.id.garage_logout -> {
                var instance = FirebaseAuth.getInstance()
                instance.signOut()
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
                finish()
            }
            R.id.garage_parking_lots -> {
                val intent = Intent(this, MainParkWindow::class.java)
                startActivity(intent)
                finish()
            }
            R.id.garage_add_car -> {
                val intent = Intent(this, CarDetailsWindow::class.java)
                startActivity(intent)
                finish()
            }
            android.R.id.home -> {
                goBack()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        goBack()
        super.onBackPressed()
    }

    private fun prepareListData(storedCars: ArrayList<CarDao>?) {

        if (storedCars == null || storedCars!!.isEmpty()) {
            listDataHeader.add("Empty garage.")
            listDataChild[listDataHeader.get(0)] = arrayListOf()
        } else {
            for ((index, carDao: CarDao) in storedCars!!.withIndex()) {
                listDataHeader.add(carDao.spz)
                val car = ArrayList<String>()
                car.add("Model : " + carDao.model)
                car.add("Color : " + carDao.color)
                car.add("Plates : " + carDao.spz)
                listDataChild[listDataHeader.get(index)] = car
            }
        }

    }
}
