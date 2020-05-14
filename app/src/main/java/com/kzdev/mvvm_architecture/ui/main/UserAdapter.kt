package com.kzdev.mvvm_architecture.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kzdev.mvvm_architecture.R
import com.kzdev.mvvm_architecture.data.model.User

class UserAdapter(private val context: Context) :
        RecyclerView.Adapter<UserAdapter.MyViewHolder>(){
    private val userList = mutableListOf<User>()
    inner class MyViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview){
        private val name_tv : TextView = itemview.findViewById(R.id.name_tv)
        private val email_tv : TextView = itemview.findViewById(R.id.email_tv)
        private val avatar_img : ImageView = itemview.findViewById(R.id.avatar_img)
        fun bind(user: User){
            name_tv.text = user.name
            email_tv.text = user.email
            Glide.with(context)
                .load(user.avatar)
                .into(avatar_img)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout,parent,false))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        holder.bind(userList[position])
    }
    fun updateList(list: List<User>){
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

}