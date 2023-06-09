package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirvan.moviedb.Core.util.shimmerEffect

@Composable
fun ShimmerGenres(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Row(
            modifier = modifier
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .height(40.dp)
                    .shimmerEffect()
            )
        }
    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun prevShimmerDistributor() {
}
