package com.mirvan.moviedb.App_feature.domain.model

import com.mirvan.moviedb.App_feature.presentation.genre.GenreState

data class Genres(
    val genres: List<Genre>?
) {
    data class Genre(
        val id: Int?,
        val name: String?
    )

    fun toGenreState(): GenreState {
        return GenreState(
            genreData = Genres(
                genres = genres
            )
        )
    }
}