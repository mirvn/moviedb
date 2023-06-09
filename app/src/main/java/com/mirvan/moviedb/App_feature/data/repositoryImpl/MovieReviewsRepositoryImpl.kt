package com.mirvan.moviedb.App_feature.data.repositoryImpl // ktlint-disable package-name

import android.util.Log
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.domain.model.MovieReview
import com.mirvan.moviedb.App_feature.domain.repository.MovieReviewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class MovieReviewsRepositoryImpl(
    private val api: MainApi
) : MovieReviewsRepository {
    override fun getMovieReview(
        movieId: Int,
        page: String
    ): Flow<Resource<MovieReview>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteReviewsData = api.getMovieReviews(
                movie_id = movieId,
                page = page
            )
            if (remoteReviewsData.isSuccessful) {
                val remoteToDto = remoteReviewsData.body()
                emit(
                    Resource.Success(
                        data = remoteToDto?.toMovieReview()
                    )
                )
            } else {
                val contentType = remoteReviewsData.headers().get("Content-Type")
                val errorBody = remoteReviewsData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody.toString())
                    val errorMessage = errorJson.getString("message")
                    emit(Resource.Error(message = errorMessage, data = null))
                }
            }
        } catch (e: HttpException) {
            // in case for invalid response
            emit(
                Resource.Error(
                    message = e.message(),
                    `data` = null
                )
            )
        } catch (e: IOException) {
            // wrong parsing, server error, or no connection case
            emit(
                Resource.Error(
                    message = e.message,
                    `data` = null
                )
            )
        }
    }
}
