package com.example.jikan

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.jikan.databinding.FragmentAnimeDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "info"


class AnimeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var animeInfo: AnimeInfo? = null
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private var expanded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeInfo = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val layoutTransition = root.layoutTransition
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            animeDetailAnimeName.apply {
                text = animeInfo?.Title

            }
            animeDetailFragmentSynopsis.text = animeInfo?.synopsis
            fragmentAnimeDetailExpandButton.setOnClickListener {
                Log.i("button state" , if(expanded) "expanded" else "collapsed")
                if (expanded) {
                    animeDetailFragmentSynopsis.maxLines = 5
                    expanded = false
                    fragmentAnimeDetailExpandButton.text =
                        getString(R.string.fragment_anime_detail_expand_button_label)
                } else {
                    animeDetailFragmentSynopsis.maxLines = Int.MAX_VALUE
                    expanded = true
                    fragmentAnimeDetailExpandButton.text =
                        getString(R.string.fragment_anime_detail_collapse_button_label)
                }
            }

            Picasso.get().load(animeInfo?.imageUrl).into(animeDetailFragmentImage)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            lifecycleScope.launch {
                delay(100)
                if (animeDetailFragmentSynopsis.lineCount <=5) fragmentAnimeDetailExpandButton.visibility =
                    View.INVISIBLE
                Log.i("lines count", animeDetailFragmentSynopsis.lineCount.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnimeDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AnimeInfo) =
            AnimeDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}