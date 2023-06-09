package com.mirvan.moviedb.App_feature.data.repositoryImpl // ktlint-disable package-name

import android.util.Log
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.domain.model.MovieDetail
import com.mirvan.moviedb.App_feature.domain.repository.DetailMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class DetailMovieRepositoryImpl(
    private val api: MainApi
) : DetailMovieRepository {
    override fun getDetailMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteGenresData = api.getMovieDetail(
                language = language,
                movie_id = movieId
            )
            if (remoteGenresData.isSuccessful) {
                val remoteToDto = remoteGenresData.body()
                emit(
                    Resource.Success(
                        data = remoteToDto?.toMovieDetail()
                    )
                )
                Log.e("TAG", "getDetailMovie-success-remoteToDto?.toMovieDetail(: ${remoteToDto?.toMovieDetail()}")
            } else {
                val contentType = remoteGenresData.headers().get("Content-Type")
                val errorBody = remoteGenresData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody.toString())
                    val errorMessage = errorJson.getString("status_message")
                    emit(Resource.Error(message = errorMessage, data = null))
                    Log.e("TAG", "getDetailMovie-error-errorJson(: $errorJson")
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
            Log.e("TAG", "getDetailMovie-error-HttpException(: ${e.message()}")
        } catch (e: IOException) {
            // wrong parsing, server error, or no connection case
            emit(
                Resource.Error(
                    message = e.message,
                    `data` = null
                )
            )
            Log.e("TAG", "getDetailMovie-error-IOException(: ${e.message}")
        }
    }
}
