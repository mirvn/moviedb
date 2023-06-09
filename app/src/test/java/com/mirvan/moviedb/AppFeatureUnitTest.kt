package com.mirvan.moviedb

import com.example.moviedb.Core.util.Resource
import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.data.repositoryImpl.GetGenresRepositoryImpl
import com.mirvan.moviedb.App_feature.domain.model.Genres
import com.mirvan.moviedb.utils.enqueueResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppFeatureUnitTest {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient.Builder()
        .build()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MainApi::class.java)
    private val getGenresRepository = GetGenresRepositoryImpl(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch get genres correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-genres-response-200.json", 200)

        runBlocking {
            getGenresRepository.getGenres("en").collectLatest { result ->
                val expected = Genres(
                    genres = listOf(
                        Genres.Genre(
                            id = 28,
                            name = "Action"
                        ),
                        Genres.Genre(
                            id = 12,
                            name = "Adventure"
                        ),
                        Genres.Genre(
                            id = 16,
                            name = "Animation"
                        ),
                        Genres.Genre(
                            id = 35,
                            name = "Comedy"
                        ),
                        Genres.Genre(
                            id = 80,
                            name = "Crime"
                        ),
                        Genres.Genre(
                            id = 99,
                            name = "Documentary"
                        ),
                        Genres.Genre(
                            id = 18,
                            name = "Drama"
                        ),
                        Genres.Genre(
                            id = 10751,
                            name = "Family"
                        ),
                        Genres.Genre(
                            id = 14,
                            name = "Fantasy"
                        ),
                        Genres.Genre(
                            id = 36,
                            name = "History"
                        ),
                        Genres.Genre(
                            id = 27,
                            name = "Horror"
                        ),
                        Genres.Genre(
                            id = 10402,
                            name = "Music"
                        ),
                        Genres.Genre(
                            id = 9648,
                            name = "Mystery"
                        ),
                        Genres.Genre(
                            id = 10749,
                            name = "Romance"
                        ),
                        Genres.Genre(
                            id = 878,
                            name = "Science Fiction"
                        ),
                        Genres.Genre(
                            id = 10770,
                            name = "TV Movie"
                        ),
                        Genres.Genre(
                            id = 53,
                            name = "Thriller"
                        ),
                        Genres.Genre(
                            id = 10752,
                            name = "War"
                        ),
                        Genres.Genre(
                            id = 37,
                            name = "Western"
                        )
                    )
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: Genres = result.data!!
                        assertEquals(expected.genres?.size, actual.genres?.size)
                        for (i in 0 until expected.genres?.size!!) {
                            val expectedEmployee = expected.genres!![i]
                            val actualEmployee = actual.genres!![i]

                            assertEquals(expectedEmployee.id, actualEmployee.id)
                            assertEquals(
                                expectedEmployee.name,
                                actualEmployee.name
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `should fetch get all employees given 401 response`() {
        mockWebServer.enqueueResponse("get-genres-response-401.json", 401)

        runBlocking {
            getGenresRepository.getGenres("en").collectLatest { result ->
                val expected = "Invalid API key: You must be granted a valid key."
                when (result) {
                    is Resource.Error -> {
                        val actual: String? = result.message
                        assertEquals(expected, actual)
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
