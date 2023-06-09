package com.mirvan.moviedb.App_feature.data.remote.dto

import com.mirvan.moviedb.App_feature.domain.model.MovieReview

data class MovieReviewDto(
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

    fun toMovieReview(): MovieReview {
        val resultData = mutableListOf<MovieReview.Result>()
        results?.map {
            val authorDetailsData = MovieReview.Result.AuthorDetails(
                avatar_path = it.author_details?.avatar_path,
                name = it.author_details?.name,
                rating = it.author_details?.rating,
                username = it.author_details?.username
            )
            resultData.add(
                MovieReview.Result(
                    author = it.author,
                    author_details = authorDetailsData,
                    content = it.content,
                    created_at = it.created_at,
                    id = it.id,
                    updated_at = it.updated_at,
                    url = it.url
                )
            )
        }
        return MovieReview(
            id = id,
            page = page,
            results = resultData,
            total_pages = total_pages,
            total_results = total_results
        )
    }
}
