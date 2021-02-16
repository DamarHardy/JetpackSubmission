package com.damar.jetpacksubmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.jetpacksubmission.databinding.FragmentTvBinding
import com.damar.jetpacksubmission.local.LocalDatabase
import com.damar.jetpacksubmission.repository.RemoteRepo
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.home.adapter.PagerAdapter
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.ui.home.misc.PageTransformer
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import com.damar.jetpacksubmission.utils.getViewModel
import kotlinx.coroutines.Dispatchers

class TvFragment : Fragment() {
    private val homeVM by lazy {
        requireActivity().getViewModel{ HomeViewModel(Repository(LocalDatabase.getInstance(requireContext().applicationContext).localRepo, RemoteRepo.instance, Dispatchers.IO)) }
    }
    private lateinit var binding: FragmentTvBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        homeVM.tvTrending.observe(viewLifecycleOwner,{
            if(it!=null){
                val pagerAdapter = PagerAdapter(it, homeVM)
                binding.tvPager.adapter = pagerAdapter
                binding.tvPager.setPageTransformer(PageTransformer())
                pagerAdapter.notifyDataSetChanged()
            }
        })
        homeVM.tvPopular.observe(viewLifecycleOwner, {
            if(it!=null){
                val rvAdapter = PopularMovieAdapter(it, homeVM)
                binding.tvRv.adapter = rvAdapter
                binding.tvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.tvRv.isNestedScrollingEnabled = false
            }
        })
        return binding.root
    }
}