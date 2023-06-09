package com.mirvan.moviedb.App_feature.data.remote.dto

import com.mirvan.moviedb.App_feature.domain.model.Genres

data class GenresDto(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    fun toGenres(): Genres {
        val genreList = mutableListOf<Genres.Genre>()
        genres.map {
            val genre = Genres.Genre(
                id = it.id,
                name = it.name
            )
            genreList.add(genre)
        }
        return Genres(
            genres = genreList
        )
    }
}
