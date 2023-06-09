package com.mirvan.moviedb.App_feature.di

import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.data.repositoryImpl.DetailMovieRepositoryImpl
import com.mirvan.moviedb.App_feature.data.repositoryImpl.GetGenresRepositoryImpl
import com.mirvan.moviedb.App_feature.data.repositoryImpl.MovieByGenresRepositoryImpl
import com.mirvan.moviedb.App_feature.data.repositoryImpl.MovieReviewsRepositoryImpl
import com.mirvan.moviedb.App_feature.domain.repository.DetailMovieRepository
import com.mirvan.moviedb.App_feature.domain.repository.GetGenresRepository
import com.mirvan.moviedb.App_feature.domain.repository.MovieByGenresRepository
import com.mirvan.moviedb.App_feature.domain.repository.MovieReviewsRepository
import com.mirvan.moviedb.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMovieReviewRepository(
        mainApi: MainApi
    ): MovieReviewsRepository {
        return MovieReviewsRepositoryImpl(
            api = mainApi
        )
    }

    @Provides
    @Singleton
    fun provideDetailMovieRepository(
        mainApi: MainApi
    ): DetailMovieRepository {
        return DetailMovieRepositoryImpl(
            api = mainApi
        )
    }

    @Provides
    @Singleton
    fun provideMovieByGenresRepository(
        mainApi: MainApi
    ): MovieByGenresRepository {
        return MovieByGenresRepositoryImpl(
            api = mainApi
        )
    }

    @Provides
    @Singleton
    fun provideGetGenresRepository(
        mainApi: MainApi
    ): GetGenresRepository {
        return GetGenresRepositoryImpl(
            api = mainApi
        )
    }

    @Provides
    @Singleton
    fun provideMainApi(): MainApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_TMDB)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainApi::class.java)
    }
}
