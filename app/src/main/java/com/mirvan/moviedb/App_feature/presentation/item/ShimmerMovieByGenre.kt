package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.douinventory.Core.util.shimmerEffect

@Composable
fun ShimmerMovieByGenre(
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
                .padding(4.dp)
                .size(width = 120.dp, height = 150.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }
    } else {
        contentAfterLoading()
    }
}
