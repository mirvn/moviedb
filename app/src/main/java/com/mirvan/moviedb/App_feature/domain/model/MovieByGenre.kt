package com.mirvan.moviedb.App_feature.domain.model

import com.mirvan.moviedb.App_feature.presentation.moviebygenre.MovieByGenreState

data class MovieByGenre(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Result(
        val adult: Boolean?,
        val backdrop_path: String?,
        val genre_ids: List<Int>?,
        val id: Int?,
        val original_language: String?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val release_date: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?
    )

    fun toMovieByGenreState(): MovieByGenreState {
        return MovieByGenreState(
            movieByGenre = MovieByGenre(
                page,
                results,
                total_pages,
                total_results
            )
        )
    }
}
