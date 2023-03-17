package com.example.jikan.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentSearchBinding
import com.example.jikan.ui.adapters.AnimePagingAdapter
import com.example.jikan.viewModels.AnimeSearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), AnimePagingAdapter.OnItemClickListener {

    val animeSearchViewModel: AnimeSearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val mAdapter = AnimePagingAdapter(this).apply {
        this.addLoadStateListener {
            when (it.prepend) {
                is LoadState.Error -> retry()
                else -> {}
            }
            when (it.append) {
                is LoadState.Error -> retry()
                else -> {}
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        with(binding.searchResult.root) {
            this.adapter = mAdapter
            this.layoutManager = GridLayoutManager(this@SearchFragment.context, 2)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) init()
    }

    private fun init() {
        val searchView = binding.searchAnime
        searchView.show()
        searchView.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val textView = searchView.editText

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                Log.i("TextWatcher", "$this on $textView text:$s ")

                lifecycleScope.launch {
                    animeSearchViewModel.searchAnime(s.toString()).collect {

                        mAdapter.submitData(it)
                    }
                }
                val stub = 0;
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        searchView.editText.addTextChangedListener(watcher)
        Log.i("textWatcher", "$watcher added")
        searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            true
        }
    }

    override fun onClick(item: AnimeInfo) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToAnimeDetailFragment(
                item
            )
        )
    }
}