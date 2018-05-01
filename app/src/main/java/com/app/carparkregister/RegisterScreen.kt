package com.app.carparkregister

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.app.carparkregister.domain.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_screen.*

/**
 *
 */
class RegisterScreen : AppCompatActivity() {

    val REQUIRED_FIELD = "This field is required."
    var database = FirebaseDatabase.getInstance()
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_register_screen)

        val submitRegister = findViewById<Button>(R.id.register_submit) as Button

        submitRegister.setOnClickListener {
            val email = register_email.text.toString()
            val passwd = register_passwd.text.toString()
            val fullName = full_name.text.toString()
            val passwdCheck = register_passwd_check.text.toString()
            val phone = register_phone.text.toString()

            if(doValidation(email, fullName, passwd, passwdCheck, phone)){
                submitNewData(email, fullName, passwd, phone)

                val intent = Intent(this, MainParkWindow::class.java)
                startActivity(intent)
            }
        }
    }

    fun doValidation(email: String, fullName: String, passwd: String, passwdCheck:String,  phone: String): Boolean {
        if(email.isBlank()){
            register_email.setError(REQUIRED_FIELD)
            return false
        }
        if(passwd.isBlank()){
            register_passwd.setError(REQUIRED_FIELD)
            return false
        }
        if(passwd.length < 6){
            register_passwd.setError("Password must have at least 6 characters.")
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
        if(fullName.isBlank()){
            full_name.setError(REQUIRED_FIELD)
            return false
        }
        if(!passwd.equals(passwdCheck)){
            register_passwd_check.setError("Password doesn't match.")
            return false
        }
        return true
    }

    fun submitNewData(email: String, fullName:String, password: String, phone: String) {
        // TODO REFACTOR REDUNDANT CODE
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val loginIntent = Intent(this, MainParkWindow::class.java)
                    startActivity(loginIntent)
                    finish()
                }
                .addOnFailureListener {
                }
        val user = UserDao(email, fullName, phone)
        val ref = database.getReference("users/")
        val key = ref.push().key
        ref.child(key).setValue(user)
    }

}
