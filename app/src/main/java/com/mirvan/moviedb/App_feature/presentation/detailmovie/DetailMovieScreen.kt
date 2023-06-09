package com.mirvan.moviedb.App_feature.presentation.detailmovie

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mirvan.moviedb.App_feature.presentation.item.*
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
    val context = LocalContext.current
    var pageState by remember {
        mutableStateOf("1")
    }
    val scrollState = rememberLazyListState()

    val movieId = navController.currentBackStackEntry?.arguments?.getString("movieId").toString()
    LaunchedEffect(Unit) {
        detailMovieViewModel.setMovieId(movieId = movieId.toInt())
        detailMovieViewModel.getReviews(movieId = movieId.toInt(), page = pageState)
    }

    var itemSize by remember {
        mutableStateOf(20)
    }
    var isLoadingDetailMovie by remember {
        mutableStateOf(true)
    }
    var isLoadingReviewMovie by remember {
        mutableStateOf(true)
    }

    val detailMovieState = detailMovieViewModel.detailMovieState.value
    val reviewMovieState = detailMovieViewModel.movieReviewsState.value

    isLoadingDetailMovie = detailMovieState.isLoading
    isLoadingReviewMovie = reviewMovieState.isLoading
    if (!isLoadingReviewMovie) itemSize = reviewMovieState.movieReview?.results?.size ?: 20

    // observe the state change of the scroll state
    val endReached by remember {
        derivedStateOf {
            scrollState.isScrollInProgress && scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == itemSize - 1
        }
    }
    if (endReached) {
        LaunchedEffect(Unit) {
            // react on scroll
            Log.e(TAG, "DetailMovieScreen-totalPage: ${reviewMovieState.movieReview?.total_pages}")
            if (pageState.toInt() != reviewMovieState.movieReview?.total_results) {
                pageState = (pageState.toInt() + 1).toString()
                detailMovieViewModel.getReviews(
                    movieId = detailMovieState.movieDetail?.id,
                    page = pageState
                )
                Log.e(TAG, "DetailMovieScreen-pageState: $pageState")
                Log.e(TAG, "DetailMovieScreen-reviewMovieState: ${reviewMovieState.movieReview?.results?.size}")
            } else {
                Toast.makeText(context, "No more reviews", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize(),
        state = scrollState
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
        items(itemSize) { index ->
            val reviewResult = reviewMovieState.movieReview?.results?.get(index)
            ShimmerMovieReviews(
                isLoading = isLoadingDetailMovie,
                contentAfterLoading = {
                    if (reviewResult != null) {
                        ItemReviews(
                            movieReview = reviewResult
                        )
                    }
                }
            )
        }
    }
}
