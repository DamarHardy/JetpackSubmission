 package com.damar.jetpacksubmission.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerSeasonBinding
import com.damar.jetpacksubmission.models.SeasonsItem
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import java.text.SimpleDateFormat
import java.util.*

 private lateinit var binding: ContainerSeasonBinding
class SeasonAdapter(private var data: List<SeasonsItem?>): RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ContainerSeasonBinding): RecyclerView.ViewHolder(binding.root){
        val imagePoster = binding.listImages
        val itemSeasonTitle = binding.seasonDetail
        val itemSeasonEpisode = binding.seasonEpisode
        val itemSeasonAiring = binding.seasonAir

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContainerSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        if(item!=null){
            Glide.with(holder.itemView.context).load(BASE_IMG_URL+item.posterPath).placeholder(R.drawable.loading_image).into(holder.imagePoster)
            holder.itemSeasonTitle.text = holder.itemView.context.getString(R.string.season_template, item.seasonNumber)
            holder.itemSeasonEpisode.text = holder.itemView.context.getString(R.string.season_episode_template, item.episodeCount)
            val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
            if(item.airDate!=null){
                val date = dateFormatter.parse(item.airDate)
                date?.let {
                    val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(it)
                    holder.itemSeasonAiring.text = holder.itemView.context.getString(R.string.season_airing_template, dateFinal)
                }
            }else{
                holder.itemSeasonAiring.text = holder.itemView.context.getString(R.string.no_info)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}