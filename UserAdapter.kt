package com.akaalwebsoft.webschoolmanager.chatapplicationandroid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.ChatScreen
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.R
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.User
import com.google.firebase.ktx.Firebase

class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentuser = userList[position]
        holder.name.text = currentuser.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatScreen::class.java)
            intent.putExtra("name", currentuser.name)
            intent.putExtra("uid", currentuser.uid)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return userList.size

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }
}