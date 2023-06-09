package com.example.moviedb.Graph

import android.annotation.SuppressLint
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviedb.Core.util.Graph
import com.mirvan.moviedb.App_feature.presentation.detailmovie.DetailMovieScreen
import com.mirvan.moviedb.App_feature.presentation.genre.GenreScreen
import com.mirvan.moviedb.App_feature.presentation.moviebygenre.MovieByGenreScreen
import com.mirvan.moviedb.R

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RootNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var topBarTitle by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(title = topBarTitle)
        }
    ) { paddingValue ->
        NavHost(
            navController = navController,
            startDestination = Graph.GENRE
        ) {
            composable(route = Graph.GENRE) {
                topBarTitle = stringResource(id = R.string.list_genre_movie)
                GenreScreen(
                    paddingValues = paddingValue,
                    navController = navController
                )
            }
            composable(route = Graph.MOVIEBYGENRE + "/{genreId}" + "/{genreName}") {
                val genreName = it.arguments?.getString("genreName")
                topBarTitle = "$genreName ${stringResource(id = R.string.movie)}"
                MovieByGenreScreen(
                    paddingValues = paddingValue,
                    navController = navController
                )
            }
            composable(route = Graph.DETAILMOVIE + "/{movieId}") {
                topBarTitle = stringResource(id = R.string.detail_movie)
                DetailMovieScreen(
                    paddingValues = paddingValue,
                    navController = navController
                )
            }
            composable(route = Graph.TRAILER) {
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String = ""
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(0.8f),
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
