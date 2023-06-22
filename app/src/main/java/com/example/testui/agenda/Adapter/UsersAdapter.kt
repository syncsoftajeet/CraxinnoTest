package com.example.craxinnotest.agenda.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.craxinnotest.agenda.model.Attendee
import com.example.testui.R
import com.example.testui.databinding.UserItemsBinding

class UsersAdapter(val attendeeslist: List<Attendee>)  : RecyclerView.Adapter<UsersAdapter.UserDataHolder>(){
    class UserDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UserItemsBinding.bind(itemView.rootView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataHolder {
        return UserDataHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_items,parent,false))

    }

    override fun getItemCount(): Int {
        return attendeeslist.size
    }

    override fun onBindViewHolder(holder: UserDataHolder, position: Int) {
        attendeeslist[position].image?.let {
            Glide.with(holder.itemView.context).load(attendeeslist[position].image).placeholder(R.drawable.userplaceholer).into(holder.binding.userimage)
        }

    }
}