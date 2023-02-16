package com.akaalwebsoft.webschoolmanager.chatapplicationandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.R
import com.akaalwebsoft.webschoolmanager.chatapplicationandroid.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MessageAdapter(val context: Context, val messagelist: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVED = 1
    val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.receivelayout, parent, false)
            return receiveViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.sendlayout, parent, false)
            return sendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage = messagelist[position]

        if (holder.javaClass == sendViewHolder::class.java) {
            //do the stuff for send view holder

            val viewHolder = holder as sendViewHolder
            holder.sendmessage.text = currentmessage.message

        } else {
            //do the stuff for receive view holder

            val viewHolder = holder as receiveViewHolder
            holder.receivemessage.text = currentmessage.message

        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage = messagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)) {
            return ITEM_SEND
        } else {
            return ITEM_RECEIVED
        }


    }

    override fun getItemCount(): Int {
        return messagelist.size
    }

    class sendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendmessage = itemView.findViewById<TextView>(R.id.txt_send_msg)

    }

    class receiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivemessage = itemView.findViewById<TextView>(R.id.txt_receive_msg)
    }

}