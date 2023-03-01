package com.example.jikan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    val animeViewModel by activityViewModels<AnimeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = view.findViewById<SearchView>(R.id.search_anime)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0 ?: return false
                Log.i("search", "submitted $p0")
                val id: Int? = p0.toIntOrNull()
                if (id == null) {
                    Toast.makeText(
                        this@SearchFragment.context,
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

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}