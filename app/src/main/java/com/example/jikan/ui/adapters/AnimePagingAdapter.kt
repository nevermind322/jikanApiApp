package com.example.jikan.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentAnimeItemBinding
import com.squareup.picasso.Picasso

class AnimePagingAdapter :
    PagingDataAdapter<AnimeInfo, AnimePagingAdapter.PagingAnimeViewHolder>(MyDiffCallback) {
    object MyDiffCallback : DiffUtil.ItemCallback<AnimeInfo>() {
        override fun areItemsTheSame(oldItem: AnimeInfo, newItem: AnimeInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AnimeInfo, newItem: AnimeInfo): Boolean {
            return oldItem == newItem
        }

    }

    inner class PagingAnimeViewHolder(binding: FragmentAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val name = binding.fragmentAnimeListItemAnimeName
        private val image = binding.fragmentAnimeListItemAnimeImage
        fun bind(item: AnimeInfo?) {
            item?.let {
                name.text = it.Title
                Picasso.get().load(it.imageUrl).into(image)
            }
        }
    }

    override fun onBindViewHolder(holder: PagingAnimeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingAnimeViewHolder {
        return PagingAnimeViewHolder(
            FragmentAnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}