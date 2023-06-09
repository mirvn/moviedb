package com.mirvan.moviedb.App_feature.presentation.detailmovie

import android.util.Log
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mirvan.moviedb.App_feature.presentation.item.ItemDetailMovie
import com.mirvan.moviedb.App_feature.presentation.item.ShimmerDetailMovie
import com.mirvan.moviedb.BuildConfig
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

private const val TAG = "DetailMovieScreen"

@Composable
fun DetailMovieScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    detailMovieViewModel: DetailMovieViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val movieId = navController.currentBackStackEntry?.arguments?.getString("movieId").toString()
//    Log.e(TAG, "DetailMovieScreen: $movieId")
    LaunchedEffect(Unit) {
        Log.e(TAG, "DetailMovieScreen-LaunchedEffect: $movieId")
        detailMovieViewModel.setMovieId(movieId = movieId.toInt())
    }

    var itemSize by remember {
        mutableStateOf(20)
    }
    var isLoadingDetailMovie by remember {
        mutableStateOf(true)
    }

    val detailMovieState = detailMovieViewModel.detailMovieState.value
    isLoadingDetailMovie = detailMovieState.isLoading
//    if (!isLoadingDetailMovie) itemSize = detailMovieState.movieDetail?.genres?.size ?: 20

    LazyColumn(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
    ) {
        item {
            ShimmerDetailMovie(
                isLoading = isLoadingDetailMovie,
                contentAfterLoading = {
                    if (detailMovieState.movieDetail != null) {
                        CoilImage(
                            imageModel = { BuildConfig.URL_TMDB_IMAGE + detailMovieState.movieDetail.poster_path },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop
                            ),
                            modifier = modifier.fillMaxWidth().height(200.dp)
                        )
                        ItemDetailMovie(
                            movieDetail = detailMovieState.movieDetail,
                            onclickTrailer = {}
                        )
                    }
                }
            )
        }
//        items(itemSize) { index ->
//            val genre = detailMovieState.movieDetail?.genres?.get(index)
//            ShimmerGenres(
//                isLoading = isLoadingDetailMovie,
//                contentAfterLoading = {
//                    if (genre != null) {
//                        ItemListGenre(
//                            genre = genre,
//                            onCLickDetail = {
//                                navController.navigate(route = Graph.MOVIEBYGENRE + "/${genre.id}" + "/${genre.name}")
//                            }
//                        )
//                    }
//                }
//            )
//        }
    }
}
