 package com.damar.jetpacksubmission.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerPagerBinding
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PagerAdapter(private var data: MutableList<*>, private var viewModel: HomeViewModel): RecyclerView.Adapter<PagerAdapter.ViewHolder>() {
    private lateinit var binding: ContainerPagerBinding
    inner class ViewHolder(binding: ContainerPagerBinding): RecyclerView.ViewHolder(binding.root){
        val imageBackdrop = binding.imageBackdrop
        val imagePoster = binding.imagePoster
        val itemTitle = binding.itemTitle
        val itemDesc = binding.itemDesc

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContainerPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(val item = data[position]){
            is Movie -> {
                holder.itemTitle.text = item.title
                holder.itemDesc.text = item.overview
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.posterPath).placeholder(R.drawable.loading_image).into(holder.imagePoster)
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.backdropPath).placeholder(R.drawable.loading_image).into(holder.imageBackdrop)
                holder.itemView.setOnClickListener {
                    if(it!=null){
                        viewModel.setSelectedMovie(item.id)
                    }
                }
            }
            is Tv -> {
                holder.itemTitle.text = item.name
                holder.itemDesc.text = item.overview
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.posterPath).placeholder(R.drawable.loading_image).into(holder.imagePoster)
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.backdropPath).placeholder(R.drawable.loading_image).into(holder.imageBackdrop)
                holder.itemView.setOnClickListener {
                    if(it!=null){
                        viewModel.setSelectedTv(item.id)
                    }
                }
            }
        }

    }
    override fun getItemCount(): Int = data.size
}