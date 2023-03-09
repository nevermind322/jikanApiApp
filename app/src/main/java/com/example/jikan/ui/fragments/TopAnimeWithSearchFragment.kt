package com.example.jikan.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jikan.R
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentSearchWithListBinding
import com.example.jikan.ui.activities.MainActivity
import com.example.jikan.viewModels.AnimeViewModel
import com.example.jikan.viewModels.TopAnimeItemState
import com.example.jikan.viewModels.TopAnimeViewModel
import kotlinx.coroutines.launch

class TopAnimeWithSearchFragment : Fragment(), MyAnimeRecyclerViewAdapter.OnItemClickListener{

    private lateinit var binding: FragmentSearchWithListBinding

    private var columnCount = 2
    private val topAnimeViewModel : TopAnimeViewModel by viewModels()


    private val animeViewModel by activityViewModels<AnimeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchWithListBinding.inflate(inflater, container, false)
        initSearch()

        val recyclerView = binding.topAnimeRecyclerView.root
        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        lifecycleScope.launch {
            topAnimeViewModel.topAnimeFlow.collect{
                Log.i("list" , it.toString())
                when (it) {
                    null -> {}
                    is TopAnimeItemState.Success -> {recyclerView.adapter = MyAnimeRecyclerViewAdapter(it.list, this@TopAnimeWithSearchFragment)}
                    is TopAnimeItemState.Error -> {showError(it.error)}
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(error : Throwable){
        Toast.makeText(this.context, (error.message ?: "Unknown error"), Toast.LENGTH_SHORT).show()
    }

    private fun initSearch(){
        val searchView = binding.search.searchAnime
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0 ?: return false
                Log.i("search", "submitted $p0")
                val id: Int? = p0.toIntOrNull()
                if (id == null) {
                    Toast.makeText(
                        this@TopAnimeWithSearchFragment.context,
                        "invalid request",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
                lifecycleScope.launch { animeViewModel.findAnimeById(id) }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    override fun OnItemCLick(item: AnimeInfo) {
        (activity as MainActivity).showAnimePage(item)
    }
}