package com.damar.jetpacksubmission.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ContainerRvBinding
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.network.NO_INFO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class PagedListAdapter: PagingDataAdapter<Any, PagedListAdapter.ViewHolder>(Comparator) {
    private lateinit var binding: ContainerRvBinding
    inner class ViewHolder(binding: ContainerRvBinding): RecyclerView.ViewHolder(binding.root){
        var itemMovie: Movie? = null
        var itemTv: Tv? = null
        val imagePoster = binding.imagePosterRv
        val itemTitle = binding.itemTitleRv
        val itemDesc = binding.itemDescRv
        val itemRelease = binding.itemReleaseRv
        val itemRating = binding.itemRatingRv

        fun bindTo(item: Any){
            if(item is Movie){
                this.itemMovie = item
            }else if (item is Tv){
                this.itemTv = item
            }
        }
    }
    @Suppress("USELESS_CAST")
    object Comparator: DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when(oldItem){
                is Movie -> oldItem.id == (newItem as Movie).id
                is Tv -> oldItem.id == (newItem as Tv).id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when(oldItem){
                is Movie -> (oldItem as Movie) == (newItem as Movie)
                is Tv -> (oldItem as Tv) == (newItem as Tv)
                else -> false
            }
        }
    }

    override fun onBindViewHolder(holder: PagedListAdapter.ViewHolder, position: Int) {
        when(val item = getItem(position)){
            is Movie -> {
                holder.bindTo(item)
                holder.itemTitle.text = item.title
                holder.itemDesc.text = item.overview
                if(item.voteAverage!=0.0){
                    holder.itemRating.text = holder.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                }else{
                    holder.itemRating.text = holder.itemView.context.getString(R.string.no_rating)
                }
                val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                if(item.releaseDate != NO_INFO){
                    val date = dateFormatter.parse(item.releaseDate)
                    val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, dateFinal)
                }else{
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, "Unknown")
                }
                Glide.with(holder.itemView.context).load(BASE_IMG_URL +item.posterPath).placeholder(
                    R.drawable.loading_image).into(holder.imagePoster)
            }
            is Tv ->{
                holder.bindTo(item)
                holder.itemTitle.text = item.name
                holder.itemDesc.text = item.overview
                if(item.voteAverage!=0.0){
                    holder.itemRating.text = holder.itemView.context.getString(R.string.rating, item.voteAverage.toString())
                }else{
                    holder.itemRating.text = holder.itemView.context.getString(R.string.no_rating)
                }
                val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
                if(item.firstAirDate != NO_INFO){
                    val date = dateFormatter.parse(item.firstAirDate)
                    val dateFinal = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, dateFinal)
                }else{
                    holder.itemRelease.text = holder.itemView.context.getString(R.string.inthea, "Unknown")
                }
                Glide.with(holder.itemView.context).load(BASE_IMG_URL +item.posterPath).placeholder(
                        R.drawable.loading_image).into(holder.imagePoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedListAdapter.ViewHolder {
        binding = ContainerRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}