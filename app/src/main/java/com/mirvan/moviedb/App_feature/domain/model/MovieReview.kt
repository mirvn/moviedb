package com.mirvan.moviedb.App_feature.domain.model

import com.mirvan.moviedb.App_feature.presentation.detailmovie.MovieReviewState

data class MovieReview(
    val id: Int?,
    val page: Int?,
    val results: List<Result>?,
    val total_pages: Int?,
    val total_results: Int?
) {
    data class Result(
        val author: String?,
        val author_details: AuthorDetails?,
        val content: String?,
        val created_at: String?,
        val id: String?,
        val updated_at: String?,
        val url: String?
    ) {
        data class AuthorDetails(
            val avatar_path: String?,
            val name: String?,
            val rating: Int?,
            val username: String?
        )
    }

    fun toMovieReviewState(): MovieReviewState {
        return MovieReviewState(
            movieReview = MovieReview(
                id = id,
                page = page,
                results = results,
                total_pages = total_pages,
                total_results = total_results
            )
        )
    }
}