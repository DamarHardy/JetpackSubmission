package com.damar.jetpacksubmission.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.FragmentTvDetailBinding
import com.damar.jetpacksubmission.databinding.PopupLoadingBinding
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.detail.adapter.BackdropsAdapter
import com.damar.jetpacksubmission.ui.detail.adapter.ImagesAdapter
import com.damar.jetpacksubmission.ui.detail.adapter.SeasonAdapter
import com.damar.jetpacksubmission.ui.detail.viewmodel.DetailViewModel
import com.damar.jetpacksubmission.utils.DataState
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvDetailFragment : Fragment() {
    private val detailVm: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentTvDetailBinding
    private lateinit var loadingBuilder: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailVm.getTvDetail(TvDetailFragmentArgs.fromBundle(requireArguments()).id)
        loadingBuilder = AlertDialog.Builder(requireContext()).setView(PopupLoadingBinding.inflate(layoutInflater).root).setCancelable(false).create()
        detailVm.detail.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Error -> println(it.e)
                is DataState.Loading -> loadingBuilder.show()
                is DataState.Success -> {
                    updateUI(it.body)
                    loadingBuilder.hide()
                    EspressoIdlingResource.decrement()
                }
            }
        })
        binding = FragmentTvDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    private fun updateUI(body: Any) {
        if(body is DetailTv){
            binding.itemTitleDetail.text = body.name
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            body.firstAirDate.let {
                println(it)
                val date = dateFormatter.parse(it)
                val finalDate = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                binding.itemReleaseDetail.text = getString(R.string.inthea, finalDate)
                binding.itemReleaseDetail2.text = finalDate
            }
            body.genres.let {
                it.forEach { item ->
                    val chip = Chip(requireContext())
                    chip.text = item.name
                    binding.genreChipGroupTv.addView(chip)

                    val chip1 = Chip(requireContext())
                    chip1.text = item.name
                    binding.otherChipGenreTv.addView(chip1)
                }
            }
            binding.itemDescDetail.text = body.overview
            binding.itemDescFullDetail.text = body.overview
            binding.itemVoteAverage.text = body.voteAverage.toString()
            binding.itemTotalVote.text = getString(R.string.braces, body.voteCount.toString())
            binding.itemCountryOriginDetail.text = this.getString(R.string.no_info)

            binding.itemLanguageDetail.text = body.originalLanguage
            binding.itemOriginalTitleDetail.text = body.originalName
            Glide.with(requireContext()).load(BASE_IMG_URL + body.posterPath).placeholder(R.drawable.loading_image).into(binding.itemPosterDetail)

            body.images.let {
                val backdropsAdapter = BackdropsAdapter(body.images.backdrops)
                binding.pagerDetailTv.adapter = backdropsAdapter

                val imagesAdapter = ImagesAdapter(body.images.posters)
                binding.imagesDetailRv.adapter = imagesAdapter
            }

            body.seasons.let {
                val seasonAdapter = SeasonAdapter(it)
                binding.seasonListRv.adapter = seasonAdapter
            }
            binding.seasonListRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.imagesDetailRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar, false)
        binding.toolbar.title = "IMovie"
    }
    companion object {
        private const val TAG = "TvDetailFragment"
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }
}