package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.BuildConfig
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemMovieByGenre(
    modifier: Modifier = Modifier,
    onCLickDetail: () -> Unit = {},
    movieData: MovieByGenre.Result
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
            )
        )
    }
}
