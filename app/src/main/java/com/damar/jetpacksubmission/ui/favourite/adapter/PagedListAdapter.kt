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
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.network.NO_INFO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class PagedListAdapter: PagingDataAdapter<Movie, PagedListAdapter.ViewHolder>(Comparator) {
    private lateinit var binding: ContainerRvBinding
    inner class ViewHolder(binding: ContainerRvBinding): RecyclerView.ViewHolder(binding.root){
        var item: Movie? = null
        val imagePoster = binding.imagePosterRv
        val itemTitle = binding.itemTitleRv
        val itemDesc = binding.itemDescRv
        val itemRelease = binding.itemReleaseRv
        val itemRating = binding.itemRatingRv

        fun bindTo(item: Movie){
            this.item = item
        }
    }
    object Comparator: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
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
//                holder.itemView.setOnClickListener {
//                    if(it!=null){
////                        viewModel.setSelectedMovie(item.id)
//                    }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedListAdapter.ViewHolder {
        binding = ContainerRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}