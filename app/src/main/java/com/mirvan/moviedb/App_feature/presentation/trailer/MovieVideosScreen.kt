package com.mirvan.moviedb.App_feature.presentation.trailer

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.mirvan.moviedb.BuildConfig

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MovieVideosScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    moviesVideosViewModel: MovieVideosViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val movieId = navController.currentBackStackEntry?.arguments?.getString("movieId").toString()
    LaunchedEffect(Unit) {
        moviesVideosViewModel.setMovieId(movieId = movieId.toInt())
    }
    val movieVideosState = moviesVideosViewModel.movieVideosState.value

    val state = rememberWebViewState(
        url = BuildConfig.URL_YOUTUBE + movieVideosState.moviesVideos?.results?.filter { result ->
            (result.type == "Trailer")
        }?.get(0)?.key.toString()
    )
    val navigator = rememberWebViewNavigator()
    // A custom WebViewClient and WebChromeClient can be provided via subclassing
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
            }
        }
    }
    WebView(
        state = state,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        },
        client = webClient
    )
    AnimatedVisibility(visible = movieVideosState.isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.small
                    )
                    .wrapContentSize()
                    .padding(4.dp)
                    .align(Alignment.Center)
            )
        }
    }
}
