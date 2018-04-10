package com.app.carparkregister

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_screen.*

/**
 *
 */
class RegisterScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_register_screen)

        val submitRegister = findViewById<Button>(R.id.register_submit) as Button



        submitRegister.setOnClickListener {
            val regName = register_name.text.toString()
            val passwd = register_passwd.text.toString()
            val passwdCheck = register_passwd_check.text.toString()
            val phone = register_phone.text.toString()
            val workPhone = register_work_phone.text.toString()
            val carType = register_car_type.text.toString()
            val carColor = register_car_color.text.toString()
            submitNewData(regName,passwd,phone,workPhone,carType,carColor)
            }

    }

    fun submitNewData(name:String, password:String, phone:String, workPhone:String, carType:String, carColor:String) {

    }
}
