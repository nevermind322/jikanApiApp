package com.example.jikan.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentAnimeDetailBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

private const val ARG_PARAM1 = "info"

class AnimeDetailFragment : Fragment() {


    //private var animeInfo: AnimeInfo? = null
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private var expanded = false
    private val args by navArgs<AnimeDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animeDetailAnimeDesc.setContent {
            Column {
                NetworkImage(url = args.info.imageUrl!!, modifier = Modifier.fillMaxWidth())
                Text(text = args.info.Title)
                TextWithExpandButton(text = args.info.synopsis)
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
        fun newInstance(param1: AnimeInfo) = AnimeDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM1, param1)
            }
        }
    }
}


data class ExpandableTextState(val lines: Int, val expanded: Boolean)


@Composable
fun NetworkImage(url: String, modifier: Modifier) {

    var image by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    var drawable by remember {
        mutableStateOf<Drawable?>(null)
    }
    DisposableEffect(url) {
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageBitmap()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                drawable = placeHolderDrawable
            }

        }
        val picasso = Picasso.get()
        picasso.load(url).into(target)
        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }
    if (image != null) {
        Image(bitmap = image!!, contentDescription = "", modifier = modifier)
    } else if (drawable != null) {
        Canvas(modifier = modifier) { drawIntoCanvas { drawable!!.draw(it.nativeCanvas) } }
    }
}

@Composable
fun TextWithExpandButton(text: String) {

    Column {
        var button by remember { mutableStateOf(false) }
        var state by remember {
            mutableStateOf(
                ExpandableTextState(lines = 6, expanded = false)
            )
        }
        val style = remember { TextStyle(color = Color.Yellow, fontSize = 12.sp) }
        val textMeasurer = rememberTextMeasurer()

        BoxWithConstraints {
            val measureResult = textMeasurer.measure(
                text,
                style = style,
                constraints = Constraints(maxWidth = constraints.maxWidth)
            )
            Log.wtf("text lines", "${measureResult.lineCount}")
            button = measureResult.lineCount > 6
            Text(
                text = text,
                style = style,
                maxLines = state.lines,
                color = Color.White,
                onTextLayout = { Log.wtf("text lines", " text ${it.lineCount}") })

        }

        val onclick = {
            state = ExpandableTextState(
                lines = if (state.expanded) 6 else Int.MAX_VALUE,
                expanded = !state.expanded
            )
        }
        if (button) {
            Button(onClick = onclick) {
                Text(text = if (state.expanded) "collapse" else "expand")
            }
        }
    }
}

