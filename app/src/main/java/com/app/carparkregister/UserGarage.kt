package com.app.carparkregister

import android.content.Context
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
import android.view.LayoutInflater
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register_screen.*


class UserGarage : AppCompatActivity() {

    val REQUIRED_FIELD = "This field is required."

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

                val mPrefs = getPreferences(Context.MODE_PRIVATE)
                val prefsEditor = mPrefs.edit()
                val gson = Gson()
                val storedCarsJson = gson.toJson(storedCars)
                prefsEditor.putString("storedCars", storedCarsJson)
                prefsEditor.commit()

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

                val layoutInflaterAndroid = LayoutInflater.from(this@UserGarage)
                val mView = layoutInflaterAndroid.inflate(R.layout.activity_car_details_window, null)
                val alertDialogBuilderUserInput = AlertDialog.Builder(this@UserGarage)
                alertDialogBuilderUserInput.setView(mView)
                val alertDialogAndroid = alertDialogBuilderUserInput.create()
                alertDialogAndroid.show()

                mView.findViewById<Button>(R.id.car_details_submit).setOnClickListener {
                    var car: CarDao = CarDao(mView.findViewById<EditText>(R.id.car_details_model).text.toString()
                            , mView.findViewById<EditText>(R.id.car_details_color).text.toString(),
                            mView.findViewById<EditText>(R.id.car_details_spz).text.toString())
                    if (doValidation(mView.findViewById<EditText>(R.id.car_details_model).text.toString(),
                                    mView.findViewById<EditText>(R.id.car_details_color).text.toString(), mView)) {
                        submitNewCar(car)
                    }
                    alertDialogAndroid.hide()
                }

            }
            android.R.id.home -> {
                goBack()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun doValidation(model: String, color: String, view: View): Boolean {
        if (model.isBlank()) {
            view.findViewById<EditText>(R.id.car_details_model).setError(REQUIRED_FIELD)
            return false
        }
        if (color.isBlank()) {
            view.findViewById<EditText>(R.id.car_details_color).setError(REQUIRED_FIELD)
            return false
        }
        return true
    }


    override fun onBackPressed() {
        goBack()
        super.onBackPressed()
    }

    fun submitNewCar(car: CarDao) {
        var storedCars: ArrayList<CarDao>? = null
        // USER SHOULD NOT BE NULL SINCE HE IS LOGGED IN
        var email = FirebaseAuth.getInstance().currentUser!!.email
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database.getReference("users/")

        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var userDao = snapshot.children.first().getValue(UserDao::class.java)
                storedCars = userDao?.cars
                var size = storedCars?.size
                if (storedCars == null) {
                    size = 0
                }
                storedCars?.add(car)
                snapshot.children.first().child("cars").ref.child(size.toString()).setValue(car)
            }
        })
    }

}
