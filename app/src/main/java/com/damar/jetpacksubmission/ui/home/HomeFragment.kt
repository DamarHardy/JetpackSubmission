
package com.damar.jetpacksubmission.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.FragmentHomeBinding
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeVM: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Load Data
        homeVM.loadData()
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewPager = binding.pager
        viewPager.adapter = FragmentPagerAdapter(this)
        binding.pager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, viewPager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Movies"
                }
                1 -> {
                    tab.text = "TV"
                }
            }
        }.attach()

        homeVM.selectedMv.observe(viewLifecycleOwner, {
            if(it!=null){
                println("$it")
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMvDetailFragment(it))
                homeVM.nullSelectedMv()
            }
        })
        homeVM.selectedTv.observe(viewLifecycleOwner, {
            if(it!=null){
                println("$it")
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvDetailFragment(it))
                homeVM.nullSelectedTv()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }
    companion object {
        private const val TAG = "HomeFragment"
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar, true)
        binding.toolbar.title = "IMovie"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_favourite -> {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavouriteFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

@ExperimentalCoroutinesApi
class FragmentPagerAdapter(homeFragment: HomeFragment): FragmentStateAdapter(homeFragment){
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MvFragment()
            1 -> TvFragment()
            else -> MvFragment()
        }
    }
}