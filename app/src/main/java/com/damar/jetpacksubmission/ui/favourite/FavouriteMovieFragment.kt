package com.damar.jetpacksubmission.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damar.jetpacksubmission.databinding.FragmentFavouriteMovieBinding
import com.damar.jetpacksubmission.ui.favourite.adapter.PagedListAdapter
import com.damar.jetpacksubmission.ui.favourite.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteMovieFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteMovieBinding
    private lateinit var adapter: PagedListAdapter
    private val viewModel : FavouriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteMovieBinding.inflate(layoutInflater, container, false)
        adapter = PagedListAdapter()
        binding.favRvMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favRvMovie.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                println("$it")
                adapter.submitData(it)
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
                return makeMovementFlags(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as PagedListAdapter.ViewHolder).item?.let {
                    //Do Delete File
                }
            }
        }).attachToRecyclerView(binding.favRvMovie)
    }
}