package com.akaalwebsoft.webschoolmanager.chatapplicationandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpScreen : AppCompatActivity() {
    private lateinit var et_name: EditText
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var mauth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        supportActionBar?.hide()

        mauth = FirebaseAuth.getInstance()

        et_name = findViewById(R.id.et_name)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
    }

    fun signupbutton(view: View) {
        var name = et_name.text.toString()
        var email = et_email.text.toString()
        var password = et_password.text.toString()

        if (!name.isEmpty() &&!email.isEmpty() &&!password.isEmpty()) {

            signup(name, email, password)
        }else{
            Toast.makeText(this,"Please Fill all Field",Toast.LENGTH_SHORT).show()
        }

    }

    private fun signup(name: String, email: String, password: String) {
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home screen
                    addusertodatabase(name, email, mauth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Some error occured", Toast.LENGTH_LONG).show()
                }
            }

        //logic of creating user

    }

    private fun addusertodatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}