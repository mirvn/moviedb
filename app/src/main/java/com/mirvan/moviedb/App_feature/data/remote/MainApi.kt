package com.mirvan.moviedb.App_feature.data.remote

import com.mirvan.moviedb.App_feature.data.remote.dto.GenresDto
import com.mirvan.moviedb.App_feature.data.remote.dto.MovieByGenreDto
import com.mirvan.moviedb.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MainApi {

    @GET("genre/movie/list")
    @Headers("Accept: application/json", "Authorization: Bearer ${BuildConfig.API_KEY_TOKEN}")
    suspend fun getGenres(
        @Query("language") language: String
    ): Response<GenresDto>

    @GET("discover/movie")
    @Headers("Accept: application/json", "Authorization: Bearer ${BuildConfig.API_KEY_TOKEN}")
    suspend fun getMovieById(
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_video") include_video: String,
        @Query("page") page: String,
        @Query("with_genres") with_genres: String
    ): Response<MovieByGenreDto>

//    @GET("discover/movie")
//    suspend fun getMoviesByGenre(
//        @Query("api_key") apiKey: String,
//        @Query("language") language: String,
//        @Query("sort_by") sort_by: String,
//        @Query("include_adult") include_adult: String,
//        @Query("include_video") include_video: String,
//        @Query("page") page: String,
//        @Query("with_genres") with_genres: String,
//        @Query("with_watch_monetization_types") with_watch_monetization_types: String
//    ): Response<MoviesByGenreResponse>
//
//    @GET("movie/{movie_id}}")
//    suspend fun getDetailMovie(
//        @Path("movie_id") movie_id: Int,
//        @Query("api_key") apiKey: String,
//        @Query("language") language: String
//    ): Response<DetailMovieResponse>
//
//    @GET("movie/{movie_id}/reviews")
//    suspend fun getReviewMovie(
//        @Path("movie_id") movie_id: Int,
//        @Query("api_key") apiKey: String,
//        @Query("language") language: String,
//        @Query("page") page: Int
//    ): Response<MovieReviewResponse>
//
//    @GET("movie/{movie_id}/videos")
//    suspend fun getMovieTrailer(
//        @Path("movie_id") movie_id: Int,
//        @Query("api_key") apiKey: String
//    ): Response<MovieTrailerResponse>
}
