package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.douinventory.Core.util.shimmerEffect

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
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.8f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
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
