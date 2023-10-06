package com.example.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import com.example.model.AnimeInfo
import com.squareup.picasso.Target


@Composable
fun DetailScreen(id: Int, vm: DetailScreenViewModel = hiltViewModel()) {

    var state: DetailScreenState by remember { mutableStateOf(DetailScreenState.Loading) }

    LaunchedEffect(Unit) {
        launch {
            state = vm.getAnime(id)
        }
    }
    when (state) {
        DetailScreenState.Loading -> {
            Text(text = "Loading")
        }

        is DetailScreenState.Success -> {
            DetailScreen((state as DetailScreenState.Success).info)
        }

        is DetailScreenState.Error -> {
            Text(text = "Error ${(state as DetailScreenState.Error).e}")
        }
    }

}

@Composable
internal fun DetailScreen(info: AnimeInfo) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        NetworkImage(
            url = info.imageUrl!!,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 8.dp)
        )
        Text(text = info.Title, color = Color.White, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        TextWithExpandButton(text = info.synopsis)
        Spacer(Modifier.height(8.dp))
        GenresList(info.genres)
    }
}

data class ExpandableTextState(val lines: Int, val expanded: Boolean)


@Composable
fun GenresList(genres: List<String>) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (genre in genres) {
            Spacer(modifier = Modifier.width(4.dp))
            GenreChip(genre)
        }
    }
}

@Composable
fun GenreChip(genre: String) {
    SuggestionChip(onClick = {}, label = { Text(genre) })
}

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

        val textMeasurer = rememberTextMeasurer()

        BoxWithConstraints(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            )
        ) {
            val measureResult = textMeasurer.measure(
                text,
                constraints = Constraints(maxWidth = constraints.maxWidth)
            )
            button = measureResult.lineCount > 6
            Text(
                text = text,
                maxLines = state.lines,
                color = Color.White,
                fontSize = 14.sp
            )
        }

        val onclick = {
            state = ExpandableTextState(
                lines = if (state.expanded) 6 else Int.MAX_VALUE,
                expanded = !state.expanded
            )
        }
        if (button) {
            Button(onClick = onclick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = if (state.expanded) "collapse" else "expand")
            }
        }
    }
}


