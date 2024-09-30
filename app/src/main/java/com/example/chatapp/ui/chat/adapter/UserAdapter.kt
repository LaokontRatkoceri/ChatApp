package com.example.chatapp.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.UserItemLayoutBinding
import com.squareup.picasso.Picasso

class UserAdapter(private var users: List<User> = emptyList(), val onItemClick: (User) -> Unit):RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder (val binding: UserItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = users[position]

        holder.binding.UsernameText.text = user!!.Email
        Picasso.get().load(user.ImageProfile).into(holder.binding.ProfileImage)


        with(holder.binding.root){
            setOnClickListener {
                onItemClick(user)
            }
        }
    }
}