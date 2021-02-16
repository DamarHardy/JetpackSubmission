package com.damar.jetpacksubmission.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.jetpacksubmission.databinding.FragmentMvBinding
import com.damar.jetpacksubmission.local.LocalDatabase
import com.damar.jetpacksubmission.repository.RemoteRepo
import com.damar.jetpacksubmission.repository.Repository
import com.damar.jetpacksubmission.ui.home.adapter.PagerAdapter
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.ui.home.misc.PageTransformer
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import com.damar.jetpacksubmission.utils.getViewModel
import kotlinx.coroutines.Dispatchers

class MvFragment : Fragment() {
    private val homeVM by lazy {
        requireActivity().getViewModel{ HomeViewModel(Repository(LocalDatabase.getInstance(requireContext().applicationContext).localRepo, RemoteRepo.instance, Dispatchers.IO)) }
    }
    private lateinit var binding: FragmentMvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMvBinding.inflate(layoutInflater, container, false)
        homeVM.mvTrending.observe(viewLifecycleOwner, {
            if(it!=null){
                val pagerAdapter = PagerAdapter(it, homeVM)
                binding.mvPager.adapter = pagerAdapter
                binding.mvPager.setPageTransformer(PageTransformer())
                pagerAdapter.notifyDataSetChanged()
            }
        })
        homeVM.mvPopular.observe(viewLifecycleOwner,{
            if(it!=null){
                println("$it")
                val rvAdapter = PopularMovieAdapter(it, homeVM)
                binding.mvRv.adapter = rvAdapter
                binding.mvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.mvRv.isNestedScrollingEnabled = false
            }
        })
        return binding.root
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }

    companion object {
        private const val TAG = "MvFragment"
    }
}