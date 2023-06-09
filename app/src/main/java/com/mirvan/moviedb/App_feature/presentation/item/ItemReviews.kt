package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mirvan.moviedb.App_feature.domain.model.MovieReview
import com.mirvan.moviedb.BuildConfig
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ItemReviews(
    modifier: Modifier = Modifier,
    movieReview: MovieReview.Result
) {
    Row {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(modifier.padding(16.dp)) {
                CoilImage(
                    imageModel = { BuildConfig.URL_TMDB_IMAGE + movieReview.author_details?.avatar_path },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop
                    ),
                    modifier = modifier
                        .size(50.dp, 50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row {
                        Text(
                            text = movieReview.author_details?.name.toString(),
                            modifier = modifier.wrapContentSize(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = movieReview.created_at.toString().substringBefore("T"),
                            modifier = modifier.wrapContentSize(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movieReview.content.toString(),
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(id = com.mirvan.moviedb.R.string.rating),
                            modifier = modifier.wrapContentSize(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = movieReview.author_details?.rating.toString(),
                            modifier = modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}
