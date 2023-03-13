package com.example.jikan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jikan.databinding.FragmentSearchBinding
import com.example.jikan.ui.adapters.AnimePagingAdapter
import com.example.jikan.viewModels.AnimeSearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    val animeSearchViewModel: AnimeSearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }


    private fun init() {

    }
}