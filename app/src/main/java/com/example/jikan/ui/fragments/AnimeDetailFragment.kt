package com.example.jikan.ui.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.jikan.data.AnimeInfo
import com.example.jikan.R
import com.example.jikan.databinding.FragmentAnimeDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "info"

class AnimeDetailFragment : Fragment() {


    //private var animeInfo: AnimeInfo? = null
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private var expanded = false
    private val args by navArgs<AnimeDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val layoutTransition = fragmentAnimeDetailConstraintLayout.layoutTransition
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            animeDetailAnimeName.apply {
                text = args.info.Title
            }

            animeDetailFragmentSynopsis.text = args.info.synopsis
            fragmentAnimeDetailExpandButton.setOnClickListener {
                if (expanded) {
                    animeDetailFragmentSynopsis.maxLines = 5
                    expanded = false
                    fragmentAnimeDetailExpandButton.text = getString(R.string.fragment_anime_detail_expand_button_label)
                } else {
                    animeDetailFragmentSynopsis.maxLines = Int.MAX_VALUE
                    expanded = true
                    fragmentAnimeDetailExpandButton.text = getString(R.string.fragment_anime_detail_collapse_button_label)
                }
            }
            Picasso.get().load(args.info.imageUrl).into(animeDetailFragmentImage)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            lifecycleScope.launch {
                delay(100)
                if (animeDetailFragmentSynopsis.lineCount <=5) fragmentAnimeDetailExpandButton.visibility =
                    View.GONE

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