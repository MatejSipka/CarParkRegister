package com.app.carparkregister

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout

import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.kitek.rvswipetodelete.SwipeToDeleteCallback

class UserGarage : AppCompatActivity() {

    private lateinit var carListView: RecyclerView
    lateinit var storedCars: ArrayList<CarDao>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_garage)

        val toolbar = findViewById<Toolbar>(R.id.garage_toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        // USER SHOULD NOT BE NULL SINCE HE IS LOGGED IN
        var email = FirebaseAuth.getInstance().currentUser!!.email
        var database = FirebaseDatabase.getInstance()

        database.getReference("users/").orderByChild("email").equalTo(email).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var userDao = snapshot.children.first().getValue(UserDao::class.java)
                if (userDao?.cars == null) {
                    storedCars = arrayListOf(CarDao())
                } else {
                    storedCars = userDao?.cars!!
                }

                carListView = findViewById(R.id.car_list_garage)
                val adapter = GarageListAdapter(storedCars)
                carListView.layoutManager = LinearLayoutManager(this@UserGarage, LinearLayout.VERTICAL, false)
                carListView.adapter = adapter

                val swipeHandler = object : SwipeToDeleteCallback(this@UserGarage) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                        val adapter = carListView.adapter as GarageListAdapter
                        val position = viewHolder!!.adapterPosition
                        adapter.removeAt(position)
                        snapshot.children.first().ref.child("cars").ref.setValue(adapter.getListData())
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)

                itemTouchHelper.attachToRecyclerView(carListView)

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

}
