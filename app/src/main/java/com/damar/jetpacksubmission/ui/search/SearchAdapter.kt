package com.damar.jetpacksubmission.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerSearchBinding
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.network.NO_INFO
import com.damar.jetpacksubmission.network.entity.SearchResultsItem
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(private val data: List<SearchResultsItem>, private val mediaType: String): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var binding: ContainerSearchBinding
    inner class ViewHolder(binding: ContainerSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: SearchResultsItem) {
            when(mediaType){
                "movie" -> {
                    Glide.with(this.itemView.context).load(BASE_IMG_URL + item.posterPath).placeholder(R.drawable.loading_image).into(photo)
                    name.text = item.title ?: NO_INFO
                    if(item.voteAverage!=0.0){
                        rating.text = this.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                    }
                    else{
                        rating.text = this.itemView.context.getString(R.string.no_rating)
                    }
                    val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                    if(item.releaseDate != NO_INFO && item.releaseDate!=null){
                        val date = dateFormatter.parse(item.releaseDate)
                        val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                        dateText.text = this.itemView.context.getString(R.string.season_airing_template, dateFinal)
                    }else{
                        dateText.text = this.itemView.context.getString(R.string.season_airing_template, "Unknown")
                    }

                }
                "person" -> {
                    Glide.with(this.itemView.context).load(BASE_IMG_URL + item.profilePath).placeholder(R.drawable.loading_image).into(photo)
                    name.text = item.name ?: NO_INFO
                    dateText.visibility = View.GONE
                    rating.visibility = View.GONE
                }
                "tv" -> {
                    Glide.with(this.itemView.context).load(BASE_IMG_URL + item.posterPath).placeholder(R.drawable.loading_image).into(photo)
                    name.text = item.name ?: NO_INFO
                    if(item.voteAverage!=0.0){
                        rating.text = this.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                    }
                    else{
                        rating.text = this.itemView.context.getString(R.string.no_rating)
                    }
                    val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                    if(item.firstAirDate != NO_INFO && item.firstAirDate!=null){
                        val date = dateFormatter.parse(item.firstAirDate)
                        val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                        dateText.text = this.itemView.context.getString(R.string.season_airing_template, dateFinal)
                    }else{
                        dateText.text = this.itemView.context.getString(R.string.season_airing_template, "Unknown")
                    }
                }
            }
        }

        private val dateText = binding.seasonAir
        private val name = binding.searchName
        private val photo = binding.listImages
        private val rating = binding.itemRatingRv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ContainerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(data[position])
    }

    override fun getItemCount(): Int = data.size
}