package com.example.viewpagers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ViewpagerImagesBinding
import com.squareup.picasso.Picasso

class pagerViewAdapter(private val imageList: List<String>) : RecyclerView.Adapter<pagerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewpagerImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Picasso.get().load(imageUrl).into(binding.ImagesPager)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewpagerImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}
