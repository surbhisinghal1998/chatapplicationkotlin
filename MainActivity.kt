package com.akaalwebsoft.webschoolmanager.chatapplicationandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.adapter.UserAdapter
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var userlist: ArrayList<User>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        recycler = findViewById(R.id.recycler)
        userlist = ArrayList()
        adapter = UserAdapter(this, userlist)
        recycler.adapter = adapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postSnapshot in snapshot.children) {
                    val currentuser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid != currentuser?.uid) {
                        userlist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout) {
            //logic for logout
            mAuth.signOut()
            val intent = Intent(this, LoginScreen::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}