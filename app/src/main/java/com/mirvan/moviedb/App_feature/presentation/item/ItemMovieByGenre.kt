package com.mirvan.moviedb.App_feature.presentation.item

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.BuildConfig
import com.mirvan.moviedb.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemMovieByGenre(
    modifier: Modifier = Modifier,
    onCLickDetail: () -> Unit = {},
    movieData: MovieByGenre.Result,
    context: Context
) {
    ElevatedCard(
        modifier = modifier
            .wrapContentSize()
            .padding(4.dp),
        onClick = onCLickDetail,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        CoilImage(
            imageModel = { BuildConfig.URL_TMDB_IMAGE + movieData.poster_path }, // loading a network image or local resource using an URL.
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop
//                alignment = Alignment.Center
            )
        )
    }
}
