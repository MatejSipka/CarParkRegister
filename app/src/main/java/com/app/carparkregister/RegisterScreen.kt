package com.app.carparkregister

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_screen.*

/**
 *
 */
class RegisterScreen : AppCompatActivity() {

    val REQUIRED_FIELD = "This field is required."
    var database = FirebaseDatabase.getInstance()

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

            if(doValidation(regName, passwd, passwdCheck, phone)){
                submitNewData(regName, passwd, phone)
            }

        }

    }

    fun doValidation(name: String, passwd: String, passwdCheck:String,  phone: String): Boolean {
        if(name.isBlank()){
            register_name.setError(REQUIRED_FIELD)
            return false
        }
        if(passwd.isBlank()){
            register_passwd.setError(REQUIRED_FIELD)
            return false
        }
        if(passwdCheck.isBlank()){
            register_passwd_check.setError(REQUIRED_FIELD)
            return false
        }
        if(phone.isBlank()){
            register_phone.setError(REQUIRED_FIELD)
            return false
        }
        if(!passwd.equals(passwdCheck)){
            register_passwd_check.setError("Password doesn't match.")
            return false
        }
        return true
    }

    fun submitNewData(name: String, password: String, phone: String) {
        val user = User(name, password)
        val ref = database.getReference("users/")
        val key = ref.push().key
        ref.child(key).setValue(user)
    }

}
