package com.app.carparkregister

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_screen.*

/**
 *
 */
class LoginScreen : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()
        if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            val intent = Intent(this, MainParkWindow::class.java)
            startActivity(intent)
            finish()
        }

        val loginRegister = findViewById<TextView>(R.id.login_register) as TextView

        login_login.setOnClickListener {
            if (doValidation(login_email.text.toString(), login_password.text.toString())) {
                doLogin(login_email.text.toString(), login_password.text.toString())
            }
        }

        loginRegister.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent)
        }
    }

    fun doValidation(email: String, pwd: String): Boolean {
        if (email.isBlank()) {
            login_email.setError(RegisterScreen().REQUIRED_FIELD)
            return false
        }
        if (pwd.isBlank()) {
            login_password.setError(RegisterScreen().REQUIRED_FIELD)
            return false
        }
        return true
    }

    fun doLogin(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener {
                    val loginIntent = Intent(this, MainParkWindow::class.java)
                    startActivity(loginIntent)
                    finish()
                }
                .addOnFailureListener {
                }
    }

}


