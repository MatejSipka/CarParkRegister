package com.app.carparkregister

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ExpandableListView
import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_car_details_window.*
import kotlinx.android.synthetic.main.activity_register_screen.*

/**
 *
 */
class CarDetailsWindow : AppCompatActivity() {

    val REQUIRED_FIELD = "This field is required."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_car_details_window)

        val toolbar = findViewById<Toolbar>(R.id.cd_toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        car_details_submit.setOnClickListener{
            var car:CarDao = CarDao(car_details_model.text.toString()
                    ,car_details_color.text.toString(), car_details_spz.text.toString())
            submitNewCar(car)
            goBack()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.getItemId() === android.R.id.home) {
            goBack()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        goBack()
        finish()
        super.onBackPressed()
    }

    fun goBack(){
        val intent = Intent(this, UserGarage::class.java)
        startActivity(intent)
    }

    fun submitNewCar(car:CarDao){
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
                if(storedCars == null){
                    size = 0
                }
                storedCars?.add(car)
                snapshot.children.first().child("cars").ref.child(size.toString()).setValue(car)
            }
        })
    }

    fun doValidation(): Boolean {

        return true
    }


}
