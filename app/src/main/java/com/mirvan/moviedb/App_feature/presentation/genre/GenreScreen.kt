package com.mirvan.moviedb.App_feature.presentation.genre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mirvan.moviedb.App_feature.presentation.item.ItemListGenre
import com.mirvan.moviedb.App_feature.presentation.item.ShimmerGenres
import com.mirvan.moviedb.Core.util.Graph

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    genresViewModel: GenresViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var itemSize by remember {
        mutableStateOf(20)
    }
    var isLoadingListEmployee by remember {
        mutableStateOf(true)
    }

    val genresState = genresViewModel.genresState.value
    isLoadingListEmployee = genresState.isLoading
    if (!isLoadingListEmployee) itemSize = genresState.genreData?.genres?.size ?: 20

    LazyColumn(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        items(itemSize) { index ->
            val genre = genresState.genreData?.genres?.get(index)
            ShimmerGenres(
                isLoading = isLoadingListEmployee,
                contentAfterLoading = {
                    if (genre != null) {
                        ItemListGenre(
                            genre = genre,
                            onCLickDetail = {
                                navController.navigate(route = Graph.MOVIEBYGENRE + "/${genre.id}" + "/${genre.name}")
                            }
                        )
                    }
                }
            )
        }
    }
}
