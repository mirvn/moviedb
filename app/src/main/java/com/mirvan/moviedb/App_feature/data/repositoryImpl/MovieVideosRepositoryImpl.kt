package com.mirvan.moviedb.App_feature.data.repositoryImpl

import android.util.Log
import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.domain.model.MoviesVideos
import com.mirvan.moviedb.App_feature.domain.repository.MovieVideosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class MovieVideosRepositoryImpl(
    private val api: MainApi
) : MovieVideosRepository {
    override fun getMovieVideos(movie_id: Int): Flow<Resource<MoviesVideos>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteMoviesData = api.getMovieVideos(movie_id = movie_id)
            if (remoteMoviesData.isSuccessful) {
                val remoteToDto = remoteMoviesData.body()
                emit(
                    Resource.Success(
                        data = remoteToDto?.toMoviesVideos()
                    )
                )
            } else {
                val contentType = remoteMoviesData.headers().get("Content-Type")
                val errorBody = remoteMoviesData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody.toString())
                    val errorMessage = errorJson.getString("status_message")
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
