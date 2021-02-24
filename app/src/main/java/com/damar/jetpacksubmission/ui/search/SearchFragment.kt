package com.damar.jetpacksubmission.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.jetpacksubmission.databinding.FragmentSearchBinding
import com.damar.jetpacksubmission.network.entity.SearchResultsItem
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.search.viewmodel.SearchViewModel
import com.damar.jetpacksubmission.utils.DataState
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getJob().cancelChildren(null)
                viewModel.getJob().cancel(null)
                if(query!=null){
                    viewModel.getSearchResult(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getJob().cancelChildren(null)
                viewModel.getJob().cancel(null)
                if (newText != null && newText.length > 3) {
                    viewModel.getSearchResult(newText)
                }
                return true
            }
        })

        viewModel.movieList.observe(viewLifecycleOwner, {
            when(it){
                is DataState.Success -> updateMovieUI(it.body)
                else -> println("Something Else Happen")
            }
        })
        viewModel.personList.observe(viewLifecycleOwner, {
            when(it){
                is DataState.Success -> updatePersonUI(it.body)
                else -> println("Something Else Happen")
            }
        })
        viewModel.tvList.observe(viewLifecycleOwner, {
            when(it){
                is DataState.Success -> updateTVUI(it.body)
                else -> println("Something Else Happen")
            }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar, false)
    }
    private fun updateTVUI(body: List<SearchResultsItem>) {
        if(body.isNotEmpty()){
            val adapter = SearchAdapter(body, "tv")
            binding.searchTvRv.adapter = adapter
            binding.searchTvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.tvCard.visibility = View.VISIBLE
        }else{
            binding.tvCard.visibility = View.GONE
        }
        EspressoIdlingResource.decrement()
    }

    private fun updatePersonUI(body: List<SearchResultsItem>) {
        if(body.isNotEmpty()){
            val adapter = SearchAdapter(body, "person")
            binding.searchPersonRv.adapter = adapter
            binding.searchPersonRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.personCard.visibility = View.VISIBLE
        }else{
            binding.personCard.visibility = View.GONE
        }
        EspressoIdlingResource.decrement()
    }

    private fun updateMovieUI(body: List<SearchResultsItem>) {
        if(body.isNotEmpty()){
            val adapter = SearchAdapter(body, "movie")
            binding.searchMvRv.adapter = adapter
            binding.searchMvRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.movieCard.visibility = View.VISIBLE
        }else{
            binding.movieCard.visibility = View.GONE
        }
        EspressoIdlingResource.decrement()
    }


}