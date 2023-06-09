package com.mirvan.moviedb.App_feature.presentation.moviebygenre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviedb.Core.util.Graph
import com.mirvan.moviedb.App_feature.presentation.item.ItemMovieByGenre
import com.mirvan.moviedb.App_feature.presentation.item.ShimmerMovieByGenre
import kotlinx.coroutines.delay

@Composable
fun MovieByGenreScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    movieByGenresViewModel: MovieByGenresViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val genreId = navController.currentBackStackEntry?.arguments?.getString("genreId")
    var pageState by remember {
        mutableStateOf("1")
    }
    val scrollState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        delay(1500)
        movieByGenresViewModel.getMovieByGenres(
            language = "eng-US",
            sort_by = "popularity.desc",
            include_video = "`true`",
            page = pageState,
            with_genres = genreId.toString()
        )
    }

    var itemSize by remember {
        mutableStateOf(30)
    }
    var isLoadingListMovieByGenre by remember {
        mutableStateOf(true)
    }
    // observe the state change of the scroll state
    val endReached by remember {
        derivedStateOf {
            scrollState.isScrollInProgress && scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == itemSize - 1
        }
    }
    if (endReached) {
        LaunchedEffect(Unit) {
            // react on scroll
            pageState = (pageState.toInt() + 1).toString()
            movieByGenresViewModel.getMovieByGenres(
                language = "eng-US",
                sort_by = "popularity.desc",
                include_video = "`true`",
                page = pageState,
                with_genres = genreId.toString()
            )
        }
    }
    val movieByGenresState = movieByGenresViewModel.movieByGenresState.value
    isLoadingListMovieByGenre = movieByGenresState.isLoading
    if (!isLoadingListMovieByGenre) itemSize = movieByGenresState.movieByGenre?.results?.size ?: 30

    LazyVerticalGrid(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        content = {
            items(itemSize) { index ->
                val movieByGenre = movieByGenresState.movieByGenre?.results?.get(index)
                ShimmerMovieByGenre(
                    isLoading = isLoadingListMovieByGenre,
                    contentAfterLoading = {
                        if (movieByGenre != null) {
                            ItemMovieByGenre(
                                movieData = movieByGenre,
                                onCLickDetail = {
                                    navController.navigate(route = Graph.DETAILMOVIE + "/${movieByGenre.id}")
                                }
                            )
                        }
                    }
                )
            }
        },
        columns = GridCells.Fixed(3),
        state = scrollState
    )
}
