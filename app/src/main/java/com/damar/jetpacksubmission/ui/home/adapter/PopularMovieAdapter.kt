package com.damar.jetpacksubmission.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerRvBinding
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

private lateinit var binding: ContainerRvBinding
class PopularMovieAdapter(private var data: MutableList<*>, private val viewModel: HomeViewModel): RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ContainerRvBinding): RecyclerView.ViewHolder(binding.root){
        val imagePoster = binding.imagePosterRv
        val itemTitle = binding.itemTitleRv
        val itemDesc = binding.itemDescRv
        val itemRelease = binding.itemReleaseRv
        val itemRating = binding.itemRatingRv

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContainerRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(val item = data[position]){
            is Movie -> {
                holder.itemTitle.text = item.title
                holder.itemDesc.text = item.overview
                if(item.voteAverage!=0.0 && item.voteAverage!=null){
                    holder.itemRating.text = holder.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                }else{
                    holder.itemRating.text = holder.itemView.context.getString(R.string.no_rating)
                }
                val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                val date = dateFormatter.parse(item.releaseDate!!)
                date?.let {
                    val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(it)
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, dateFinal)
                }
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.posterPath).placeholder(R.drawable.loading_image).into(holder.imagePoster)
                holder.itemView.setOnClickListener {
                    if(it!=null){
//                        viewModel.setSelectedMv(item.id!!)
                    }
                }
            }
            is Tv -> {
                holder.itemTitle.text = item.name
                holder.itemDesc.text = item.overview
                if(item.voteAverage!=0.0 && item.voteAverage!=null){
                    holder.itemRating.text = holder.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                }else{
                    holder.itemRating.text = holder.itemView.context.getString(R.string.no_rating)
                }
                val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                val date = dateFormatter.parse(item.firstAirDate!!)
                date?.let {
                    val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(it)
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, dateFinal)
                }
                Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.posterPath).placeholder(R.drawable.loading_image).into(holder.imagePoster)
                holder.itemView.setOnClickListener {
                    if(it!=null){
//                        viewModel.setSelectedTv(item.id!!)
                    }
                }
            }
        }

    }
    override fun getItemCount(): Int = data.size
}