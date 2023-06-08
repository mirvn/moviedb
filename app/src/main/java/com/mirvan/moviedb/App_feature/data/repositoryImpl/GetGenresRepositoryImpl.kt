package com.mirvan.moviedb.App_feature.data.repositoryImpl

import android.util.Log
import com.example.douinventory.Core.util.Resource
import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.domain.model.Genres
import com.mirvan.moviedb.App_feature.domain.repository.GetGenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class GetGenresRepositoryImpl(
    private val api: MainApi
) : GetGenresRepository {
    override fun getGenres(language: String): Flow<Resource<Genres>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteGenresData = api.getGenres(language = language)
            if (remoteGenresData.isSuccessful) {
                val remoteToDto = remoteGenresData.body()
                emit(
                    Resource.Success(
                        data = remoteToDto?.toGenres()
                    )
                )
            } else {
                val contentType = remoteGenresData.headers().get("Content-Type")
                val errorBody = remoteGenresData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody)
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
