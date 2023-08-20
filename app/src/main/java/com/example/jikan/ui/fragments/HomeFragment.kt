package com.example.jikan.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentHomeBinding
import com.example.jikan.ui.activities.MainActivity
import com.example.jikan.ui.adapters.MyAnimeRecyclerViewAdapter
import com.example.jikan.viewModels.TopAnimeItemState
import com.example.jikan.viewModels.TopAnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HomeFragment : Fragment(), MyAnimeRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private var columnCount = 2
    private val topAnimeViewModel: TopAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.searchBar.setOnClickListener {
            goToSearchFragment()
        }

        val recyclerView = binding.topAnimeRecyclerView.list
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        loadTop()
        Log.d("fragment", "loading")
        binding.topAnimeRecyclerView.root.setOnRefreshListener { loadTop() }

        return binding.root
    }

    private fun loadTop() {
        val recyclerView = binding.topAnimeRecyclerView.list
        lifecycleScope.launch {
            topAnimeViewModel.topAnimeFlow.collect {
                when (it) {
                    null -> {}
                    is TopAnimeItemState.Success -> {
                        recyclerView.adapter =
                            MyAnimeRecyclerViewAdapter(it.list, this@HomeFragment)
                    }

                    is TopAnimeItemState.Error -> {
                        showError(it.error)
                    }
                }
            }
        }
        lifecycleScope.launch {
            delay(300)
            binding.topAnimeRecyclerView.root.isRefreshing = false
        }
    }

    private fun goToSearchFragment() {
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(direction)

    }

    private fun showError(error: Throwable) {
        Toast.makeText(this.context, (error.message ?: "Unknown error"), Toast.LENGTH_SHORT).show()
    }


    override fun OnItemCLick(item: AnimeInfo) {
        (activity as MainActivity).showAnimePage(item)
    }


}