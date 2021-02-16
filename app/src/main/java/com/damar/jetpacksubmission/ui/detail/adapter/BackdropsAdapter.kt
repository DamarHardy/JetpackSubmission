 package com.damar.jetpacksubmission.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerBackdropsDetailBinding
import com.damar.jetpacksubmission.models.BackdropsItem
import com.damar.jetpacksubmission.network.BASE_IMG_URL

 private lateinit var binding: ContainerBackdropsDetailBinding
class BackdropsAdapter(private var data: List<BackdropsItem?>): RecyclerView.Adapter<BackdropsAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ContainerBackdropsDetailBinding): RecyclerView.ViewHolder(binding.root){
        val imageBackdrop = binding.listBackdrops

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContainerBackdropsDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        Glide.with(holder.itemView.context).load(BASE_IMG_URL+item?.filePath).placeholder(R.drawable.loading_image).into(holder.imageBackdrop)

    }
    override fun getItemCount(): Int = data.size
}