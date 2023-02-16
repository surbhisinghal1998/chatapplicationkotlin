package com.akaalwebsoft.webschoolmanager.chatapplicationandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

    }

    fun login(view: View) {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
            loginuser(email, password)
        } else {
            Toast.makeText(this, "Please Field all Field", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginuser(email: String, password: String) {
        //logic for login user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {


                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

    }

    fun signup(view: View) {
        var intent = Intent(this, SignUpScreen::class.java)
        startActivity(intent)
        finish()
    }
}