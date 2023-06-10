package com.mirvan.moviedb

import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.data.repositoryImpl.DetailMovieRepositoryImpl
import com.mirvan.moviedb.App_feature.data.repositoryImpl.GetGenresRepositoryImpl
import com.mirvan.moviedb.App_feature.data.repositoryImpl.MovieByGenresRepositoryImpl
import com.mirvan.moviedb.App_feature.domain.model.Genres
import com.mirvan.moviedb.App_feature.domain.model.MovieByGenre
import com.mirvan.moviedb.App_feature.domain.model.MovieDetail
import com.mirvan.moviedb.Core.util.Resource
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
    private val movieByGenresRepository = MovieByGenresRepositoryImpl(api)
    private val detailMovieRepository = DetailMovieRepositoryImpl(api)

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
                            val expectedGenre = expected.genres!![i]
                            val actualGenre = actual.genres!![i]

                            assertEquals(expectedGenre.id, actualGenre.id)
                            assertEquals(
                                expectedGenre.name,
                                actualGenre.name
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `shouldn't fetch get all genres given 401 response`() {
        mockWebServer.enqueueResponse("error-response-401.json", 401)

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

    @Test
    fun `should fetch get movies by genres correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-movies-by-genre-response-200.json", 200)

        runBlocking {
            movieByGenresRepository.getMovieByGenres(
                "en",
                "popularity.desc",
                "false",
                "1",
                "28"
            ).collectLatest { result ->
                val expected = MovieByGenre(
                    page = 1,
                    results = listOf(
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        ),
                        MovieByGenre.Result(
                            adult = false,
                            backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
                            genre_ids = listOf(
                                28,
                                53,
                                80
                            ),
                            id = 603692,
                            original_language = "en",
                            original_title = "John Wick: Chapter 4",
                            overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                            popularity = 3701.122,
                            poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                            release_date = "2023-03-22",
                            title = "John Wick: Chapter 4",
                            video = false,
                            vote_average = 7.9,
                            vote_count = 2787
                        )
                    ),
                    total_pages = 1829,
                    total_results = 36569
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: MovieByGenre = result.data!!
                        assertEquals(expected.results.size, actual.results.size)
                        assertEquals(expected, actual)
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `shouldn't fetch get movies by genre given 401 response`() {
        mockWebServer.enqueueResponse("error-response-401.json", 401)

        runBlocking {
            movieByGenresRepository.getMovieByGenres(
                "en",
                "popularity.desc",
                "false",
                "1",
                "28"
            ).collectLatest { result ->
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

    @Test
    fun `should fetch get detail movie correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-detail-movie-response-200.json", 200)

        runBlocking {
            detailMovieRepository.getDetailMovie(603692, "en").collectLatest { result ->
                val expected = MovieDetail(
                    adult = false,
                    belongs_to_collection = MovieDetail.BelongsToCollection(
                        id = 404609,
                        name = "John Wick Collection",
                        poster_path = "/xUidyvYFsbbuExifLkslpcd8SMc.jpg",
                        backdrop_path = "/fSwYa5q2xRkBoOOjueLpkLf3N1m.jpg"
                    ),
                    budget = 90000000,
                    genres = listOf(
                        MovieDetail.Genre(
                            id = 28,
                            name = "Action"
                        ),
                        MovieDetail.Genre(
                            id = 53,
                            name = "Thriller"
                        ),
                        MovieDetail.Genre(
                            id = 80,
                            name = "Crime"
                        )
                    ),
                    homepage = "https://johnwick.movie",
                    id = 603692,
                    imdb_id = "tt10366206",
                    original_language = "en",
                    original_title = "John Wick: Chapter 4",
                    overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
                    popularity = 3701.122,
                    poster_path = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
                    production_companies = listOf(
                        MovieDetail.ProductionCompany(
                            id = 3528,
                            logo_path = "/cCzCClIzIh81Fa79hpW5nXoUsHK.png",
                            name = "Thunder Road",
                            origin_country = "US"
                        ),
                        MovieDetail.ProductionCompany(
                            id = 23008,
                            logo_path = "/5SarYupipdiejsEqUkwu1SpYfru.png",
                            name = "87Eleven",
                            origin_country = "US"
                        ),
                        MovieDetail.ProductionCompany(
                            id = 491,
                            logo_path = "/5LvDUt3KmvRnXQ4NrdWJYHeuA8J.png",
                            name = "Summit Entertainment",
                            origin_country = "US"
                        ),
                        MovieDetail.ProductionCompany(
                            id = 264,
                            logo_path = "/fA90qwUKgPhMONqtwY60GaHRyrW.png",
                            name = "Studio Babelsberg",
                            origin_country = "DE"
                        )
                    ),
                    production_countries = listOf(
                        MovieDetail.ProductionCountry(
                            iso_3166_1 = "DE",
                            name = "Germany"
                        ),
                        MovieDetail.ProductionCountry(
                            iso_3166_1 = "US",
                            name = "United States of America"
                        )
                    ),
                    release_date = "2023-03-22",
                    revenue = 431769198,
                    runtime = 170,
                    spoken_languages = listOf(
                        MovieDetail.SpokenLanguage(
                            english_name = "Arabic",
                            iso_639_1 = "ar",
                            name = "العربية"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "Cantonese",
                            iso_639_1 = "cn",
                            name = "广州话 / 廣州話"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "English",
                            iso_639_1 = "en",
                            name = "English"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "French",
                            iso_639_1 = "fr",
                            name = "Français"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "German",
                            iso_639_1 = "de",
                            name = "Deutsch"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "Japanese",
                            iso_639_1 = "ja",
                            name = "日本語"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "Latin",
                            iso_639_1 = "la",
                            name = "Latin"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "Russian",
                            iso_639_1 = "ru",
                            name = "Pусский"
                        ),
                        MovieDetail.SpokenLanguage(
                            english_name = "Spanish",
                            iso_639_1 = "es",
                            name = "Español"
                        )
                    ),
                    status = "Released",
                    tagline = "No way back, one way out.",
                    title = "John Wick: Chapter 4",
                    video = false,
                    vote_average = 7.949,
                    vote_count = 2792,
                    backdrop_path = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg"
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: MovieDetail = result.data!!
                        assertEquals(expected.genres?.size, actual.genres?.size)
                        for (i in 0 until expected.genres?.size!!) {
                            val expectedGenre = expected.genres!![i]
                            val actualGenre = actual.genres!![i]

                            assertEquals(expectedGenre.id, actualGenre.id)
                            assertEquals(
                                expectedGenre.name,
                                actualGenre.name
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `shouldn't fetch detail movies by genre given 401 response`() {
        mockWebServer.enqueueResponse("error-response-401.json", 401)

        runBlocking {
            detailMovieRepository.getDetailMovie(
                603692,
                "en"
            ).collectLatest { result ->
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
