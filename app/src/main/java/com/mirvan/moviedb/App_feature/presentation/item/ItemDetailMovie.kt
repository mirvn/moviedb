package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mirvan.moviedb.App_feature.domain.model.MovieDetail

@Composable
fun ItemDetailMovie(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onclickTrailer: () -> Unit = {}
) {
    val genreNameList = mutableListOf<String>()
    movieDetail.genres?.map { genre ->
        genre.name.let { name ->
            if (name != null) {
                genreNameList.add(name)
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = movieDetail.original_title.toString(),
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = genreNameList.joinToString(", "),
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .wrapContentSize()
//                .align(Alignment.Start)
        ) {
            Text(
                text = movieDetail.status.toString(),
                modifier = modifier.wrapContentSize().padding(end = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = movieDetail.release_date.toString(),
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        Button(
            onClick = onclickTrailer,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(id = com.mirvan.moviedb.R.string.play_trailer),
                modifier = modifier
            )
            Text(
                text = stringResource(id = com.mirvan.moviedb.R.string.play_trailer),
                modifier = modifier.wrapContentSize()
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(id = com.mirvan.moviedb.R.string.content_detail_movie_description),
            modifier = modifier.wrapContentSize(),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = movieDetail.overview.toString(),
            modifier = modifier.wrapContentSize(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(id = com.mirvan.moviedb.R.string.user_review_movie_detail),
            modifier = modifier.wrapContentSize(),
            style = MaterialTheme.typography.titleSmall
        )
        // user review item below:
    }
}
