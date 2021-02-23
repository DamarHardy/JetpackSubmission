package com.damar.jetpacksubmission.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.damar.jetpacksubmission.databinding.FragmentFavouriteBinding
import com.damar.jetpacksubmission.ui.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var pager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        pager = binding.favPager
        pager.adapter = FragmentPagerFavAdapter(this)
        pager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, pager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Movies"
                }
                1 -> {
                    tab.text = "TV"
                }
            }
        }.attach()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar, false)
        binding.toolbar.title = "Favourite"
    }
}

@ExperimentalCoroutinesApi
class FragmentPagerFavAdapter(favFragment: FavouriteFragment): FragmentStateAdapter(favFragment){
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FavouriteMovieFragment()
            1 -> FavouriteTvFragment()
            else -> FavouriteMovieFragment()
        }
    }
}