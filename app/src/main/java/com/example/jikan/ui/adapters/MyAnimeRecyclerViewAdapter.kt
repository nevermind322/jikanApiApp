package com.example.jikan.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentAnimeItemBinding

import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [AnimeInfo].
 */
class MyAnimeRecyclerViewAdapter(
     val values: List<AnimeInfo>,
     val listener : OnItemClickListener
) : RecyclerView.Adapter<MyAnimeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentAnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.apply {
            animeNameTextView.text = item.Title
            Picasso.get().load(item.imageUrl).into(animeImageView)
            itemView.setOnClickListener {listener.OnItemCLick(item)}
        }
    }

    interface OnItemClickListener{
        fun OnItemCLick(item : AnimeInfo)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val animeImageView: ImageView = binding.fragmentAnimeListItemAnimeImage
        val animeNameTextView: TextView = binding.fragmentAnimeListItemAnimeName

        override fun toString(): String {
            return super.toString() + " '" + animeNameTextView.text + "'"
        }
    }

}