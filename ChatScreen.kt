package com.akaalwebsoft.webschoolmanager.chatapplicationandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.adapter.MessageAdapter
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.Message
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatScreen : AppCompatActivity() {

    private lateinit var chatrecycler: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var send: ImageView
    private lateinit var messagelist: ArrayList<Message>
    private lateinit var adapter: MessageAdapter
    private lateinit var mDbRef: DatabaseReference
    var receiverroom: String? = null
    var senderroom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)
        mDbRef = FirebaseDatabase.getInstance().getReference()
//        val intent=Intent()
        val name = intent.getStringExtra("name")
        val receiverid = intent.getStringExtra("uid")

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        senderroom = receiverid + senderuid
        receiverroom = senderuid + receiverid

        supportActionBar?.title = name
        messagelist = ArrayList()
        chatrecycler = findViewById(R.id.chatrecycler)
        messageBox = findViewById(R.id.messageBox)
        send = findViewById(R.id.send)
        adapter = MessageAdapter(this, messagelist)
        chatrecycler.adapter = adapter
        //logic for adding data to recycler view
        mDbRef.child("chats").child(senderroom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for (postsnapshot in snapshot.children) {
                        val message = postsnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        send.setOnClickListener {
            //adding the message to database
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderuid!!)

            mDbRef.child("chats").child(senderroom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverroom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

    }


}