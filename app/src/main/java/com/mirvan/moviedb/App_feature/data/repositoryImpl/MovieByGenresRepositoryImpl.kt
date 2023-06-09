package com.mirvan.moviedb.App_feature.data.repositoryImpl // ktlint-disable package-name

import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.App_feature.domain.repository.MovieByGenresRepository
import com.mirvan.moviedb.Core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class MovieByGenresRepositoryImpl(
    private val api: MainApi
) : MovieByGenresRepository {
    override fun getMovieByGenres(
        language: String,
        sort_by: String,
        include_video: String,
        page: String,
        with_genres: String
    ): Flow<Resource<MovieByGenre>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteGenresData = api.getMovieById(
                language = language,
                sort_by = sort_by,
                include_video = include_video,
                page = page,
                with_genres = with_genres
            )
            if (remoteGenresData.isSuccessful) {
                val remoteToDto = remoteGenresData.body()
                emit(
                    Resource.Success(
                        data = remoteToDto?.toMovieByGenre()
                    )
                )
            } else {
                val contentType = remoteGenresData.headers().get("Content-Type")
                val errorBody = remoteGenresData.errorBody()?.string()

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
