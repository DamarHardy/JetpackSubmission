package com.damar.jetpacksubmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.jetpacksubmission.databinding.FragmentTvBinding
import com.damar.jetpacksubmission.ui.home.adapter.PagerAdapter
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.ui.home.misc.PageTransformer
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment() {
    private val homeVM: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentTvBinding
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        // Inflate the layout for this fragment
//        binding = FragmentTvBinding.inflate(layoutInflater, container, false)
//        homeVM.tvTrending.observe(viewLifecycleOwner,{
//            if(it!=null){
//                val pagerAdapter = PagerAdapter(it, homeVM)
//                binding.tvPager.adapter = pagerAdapter
//                binding.tvPager.setPageTransformer(PageTransformer())
//                pagerAdapter.notifyDataSetChanged()
//            }
//        })
//        homeVM.tvPopular.observe(viewLifecycleOwner, {
//            if(it!=null){
//                val rvAdapter = PopularMovieAdapter(it, homeVM)
//                binding.tvRv.adapter = rvAdapter
//                binding.tvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                binding.tvRv.isNestedScrollingEnabled = false
//            }
//        })
//        return binding.root
//    }
}