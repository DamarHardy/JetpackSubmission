package com.damar.jetpacksubmission.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damar.jetpacksubmission.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteTvFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_tv, container, false)
    }
}