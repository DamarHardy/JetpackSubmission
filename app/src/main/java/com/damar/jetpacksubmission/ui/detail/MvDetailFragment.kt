package com.damar.jetpacksubmission.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.FragmentMvDetailBinding
import com.damar.jetpacksubmission.databinding.PopupLoadingBinding
import com.damar.jetpacksubmission.local.LocalDatabase
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.network.BASE_IMG_URL
import com.damar.jetpacksubmission.repository.RemoteRepo
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.detail.adapter.BackdropsAdapter
import com.damar.jetpacksubmission.ui.detail.adapter.ImagesAdapter
import com.damar.jetpacksubmission.ui.detail.viewmodel.DetailViewModel
import com.damar.jetpacksubmission.ui.detail.viewmodel.State
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import com.damar.jetpacksubmission.utils.getViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

class MvDetailFragment : Fragment() {
    private val detailVm by lazy {
        requireActivity().getViewModel{ DetailViewModel(Repository(LocalDatabase.getInstance(requireContext().applicationContext).localRepo, RemoteRepo.instance, Dispatchers.IO), Dispatchers.IO) }
    }
    private lateinit var binding: FragmentMvDetailBinding
    private lateinit var loadingBuilder: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailVm.getDetailMv(MvDetailFragmentArgs.fromBundle(requireArguments()).id)
        loadingBuilder = AlertDialog.Builder(requireContext()).setView(PopupLoadingBinding.inflate(layoutInflater).root).setCancelable(false).create()
        detailVm.detail.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> {
                    println(it.message)
                    loadingBuilder.show()
                }
                is State.Success -> {
                    println("${it.body}")
                    updateUI(it.body)
                    loadingBuilder.hide()
                    EspressoIdlingResource.decrement()
                }
                is State.Failed -> {
                    println(it.message)
                }
            }
        })
        binding = FragmentMvDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(body: Any) {
        if(body is DetailMv){
            binding.itemTitleDetail.text = body.originalTitle
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            body.releaseDate?.let {
                println(it)
                val date = dateFormatter.parse(it)
                val finalDate = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date!!)
                binding.itemReleaseDetail.text = getString(R.string.inthea, finalDate)
                binding.itemReleaseDetail2.text = finalDate
            }
            body.genres?.let {
                it.forEach { item ->
                    val chip = Chip(requireContext())
                    chip.text = item?.name
                    binding.genreChipGroup.addView(chip)

                    val chip1 = Chip(requireContext())
                    chip1.text = item?.name
                    binding.otherChipGenre.addView(chip1)
                }
            }
            binding.itemDescDetail.text = body.overview
            binding.itemDescFullDetail.text = body.overview
            binding.itemVoteAverage.text = body.voteAverage.toString()
            binding.itemTotalVote.text = getString(R.string.braces, body.voteCount.toString())
            if(body.productionCountries == null){
                binding.itemCountryOriginDetail.text = this.getString(R.string.no_info)
            }else{
                body.productionCountries.let {
                    if(it[0] !=null){
                        binding.itemCountryOriginDetail.text = it[0]?.iso31661
                    }else{
                        binding.itemCountryOriginDetail.text = this.getString(R.string.no_info)
                    }
                }
            }
            binding.itemLanguageDetail.text = body.originalLanguage
            binding.itemOriginalTitleDetail.text = body.originalTitle
            Glide.with(requireContext()).load(BASE_IMG_URL + body.posterPath).placeholder(R.drawable.loading_image).into(binding.itemPosterDetail)
            val backdropsAdapter = BackdropsAdapter(body.images?.backdrops!!)
            val imagesAdapter = ImagesAdapter(body.images.posters!!)

            binding.pagerDetailMv.adapter = backdropsAdapter
            binding.imagesDetailRv.adapter = imagesAdapter
            binding.imagesDetailRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    companion object {
        private const val TAG = "MvDetailFragment"
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar, false)
        binding.toolbar.title = "IMovie"
    }
}