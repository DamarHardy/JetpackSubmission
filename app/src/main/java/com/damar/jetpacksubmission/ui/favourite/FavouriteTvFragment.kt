package com.damar.jetpacksubmission.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damar.jetpacksubmission.databinding.FragmentFavouriteTvBinding
import com.damar.jetpacksubmission.repository.Table
import com.damar.jetpacksubmission.ui.favourite.adapter.PagedListAdapter
import com.damar.jetpacksubmission.ui.favourite.viewmodel.FavouriteViewModel
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteTvFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteTvBinding
    private lateinit var adapter: PagedListAdapter
    private val viewModel : FavouriteViewModel by activityViewModels()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteTvBinding.inflate(layoutInflater, container, false)
        adapter = PagedListAdapter()
        binding.favRvTv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favRvTv.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tvs.collectLatest {
                println("$it")
                EspressoIdlingResource.decrement()
                adapter.submitData(it as PagingData<Any>)
            }
        }
        adapter.addLoadStateListener {
            if (adapter.itemCount > 0){
                binding.favRvTv.visibility = View.VISIBLE
                binding.illustrationText.visibility = View.GONE
                binding.illustration.visibility = View.GONE
            }else{
                binding.favRvTv.visibility = View.GONE
                binding.illustrationText.visibility = View.VISIBLE
                binding.illustration.visibility = View.VISIBLE
            }
        }

        initSwipeToDelete()
        return binding.root
    }
    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as PagedListAdapter.ViewHolder).itemTv?.let {
                    viewModel.deleteFavourite(it.id, Table.FavTv)
                }
            }
        }).attachToRecyclerView(binding.favRvTv)
    }
}