package com.example.jikan.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jikan.R
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentSearchBinding
import com.example.jikan.ui.adapters.AnimePagingAdapter
import com.example.jikan.ui.adapters.SearchQueryAdapter
import com.example.jikan.viewModels.AnimeSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), AnimePagingAdapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val animeSearchViewModel: AnimeSearchViewModel by viewModels()

    private var searchJob: Job? = null

    private lateinit var binding: FragmentSearchBinding
    private val animeAdapter = AnimePagingAdapter(this).apply {
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

    private val searchQueriesAdapter =
        SearchQueryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeSearchViewModel.setShowed(false)
        lifecycleScope.launch {
            animeSearchViewModel.pagerFlow.collectLatest {
                animeAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            val l = animeSearchViewModel.getQueries()

            searchQueriesAdapter.values.addAll(l)
            searchQueriesAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        with(binding.searchResult.list) {
            adapter = searchQueriesAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        val searchView = binding.searchAnime
        searchView.inflateMenu(R.menu.filters)

        if (!animeSearchViewModel.isShowed()) {
            searchView.show()
            animeSearchViewModel.setShowed(true)
        }

        searchView.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.toString() != "") {
                    with(binding.searchResult.list) {
                        adapter = animeAdapter
                        layoutManager = GridLayoutManager(this@SearchFragment.context, 2)
                    }
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(300)
                        if (animeSearchViewModel.searchAnimeOnType(s.toString())) animeAdapter.refresh()
                    }
                } else if (s.toString() == "") {
                    with(binding.searchResult.list) {
                        adapter = searchQueriesAdapter
                        layoutManager = LinearLayoutManager(this@SearchFragment.context)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        searchView.editText.addTextChangedListener(watcher)

        searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            val s = textView.text
            lifecycleScope.launch {
                        animeSearchViewModel.insertQuery(s.toString())
            }

            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(300)
                animeSearchViewModel.searchAnimeOnType(s.toString())
                animeAdapter.refresh()
            }
            true
        }

        binding.searchResult.root.setOnRefreshListener(this)

    }


    override fun onClick(item: AnimeInfo) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToAnimeDetailFragment(item)
        )
    }

    override fun onRefresh() {
        animeAdapter.refresh()
        lifecycleScope.launch {
            delay(300)
            binding.searchResult.root.isRefreshing = false
        }
    }
}