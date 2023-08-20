package com.example.jikan.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jikan.databinding.FragmentAnimeItemBinding
import com.example.jikan.databinding.SearchQueryItemBinding
import com.example.jikan.viewModels.SearchQueryState


class SearchQueryAdapter(val values: MutableList<SearchQueryState> = mutableListOf()) :
    RecyclerView.Adapter<SearchQueryAdapter.SearchQueryViewHolder>() {
    inner class SearchQueryViewHolder(val binding: SearchQueryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val textView: TextView = binding.query

        fun bind(queryState: SearchQueryState) {
            textView.text = queryState.query
            binding.searchQueryItem.setOnLongClickListener { queryState.onLongClick(); true }
            binding.insertHintButton.setOnClickListener {
                queryState.onClick()
                if(values.remove(queryState))
                    notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        return SearchQueryViewHolder(
            SearchQueryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: SearchQueryViewHolder, position: Int) {
        holder.bind(values[position])
    }

}