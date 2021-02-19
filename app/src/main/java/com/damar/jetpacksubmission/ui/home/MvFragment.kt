package com.damar.jetpacksubmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.jetpacksubmission.databinding.FragmentMvBinding
import com.damar.jetpacksubmission.ui.home.adapter.PagerAdapter
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.ui.home.misc.PageTransformer
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import com.damar.jetpacksubmission.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MvFragment : Fragment() {
    private val homeVM: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentMvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMvBinding.inflate(layoutInflater, container, false)
        homeVM.mvTrending.observe(viewLifecycleOwner, {
            if(it!=null && it is DataState.Success){
                val pagerAdapter = PagerAdapter(it.body.toMutableList(), homeVM)
                binding.mvPager.adapter = pagerAdapter
                binding.mvPager.setPageTransformer(PageTransformer())
                pagerAdapter.notifyDataSetChanged()
            }
        })
        homeVM.mvPopular.observe(viewLifecycleOwner,{
            if(it!=null && it is DataState.Success){
                val rvAdapter = PopularMovieAdapter(it.body.toMutableList(), homeVM)
                binding.mvRv.adapter = rvAdapter
                binding.mvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.mvRv.isNestedScrollingEnabled = false
            }
        })
        return binding.root
    }
}