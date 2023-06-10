package com.mirvan.moviedb

import com.mirvan.moviedb.App_feature.data.remote.MainApi
import com.mirvan.moviedb.App_feature.data.repositoryImpl.* // ktlint-disable no-wildcard-imports
import com.mirvan.moviedb.App_feature.domain.model.*
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
    private val reviewsRepository = MovieReviewsRepositoryImpl(api)
    private val movieVideosRepository = MovieVideosRepositoryImpl(api)

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
    fun `shouldn't fetch detail movies given 401 response`() {
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

    @Test
    fun `should fetch movie reviews correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-movie-reviews-response-200.json", 200)

        runBlocking {
            reviewsRepository.getMovieReview(713704, "en").collectLatest { result ->
                val expected = MovieReview(
                    id = 713704,
                    page = 1,
                    results = listOf(
                        MovieReview.Result(
                            author = "garethmb",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "",
                                username = "garethmb",
                                avatar_path = "/https://secure.gravatar.com/avatar/3593437cbd05cebe0a4ee753965a8ad1.jpg",
                                rating = null
                            ),
                            content = "Evil Dead Rise Gives Series Fans A Fresh Take And Plenty Of Gore\r\nUsually when a film series is embarking on a fifth outing the challenges\r\nof keeping things fresh and original yet being new and creative can be a\r\nmassive obstacle.\r\n\r\nHorror series ranging  from “Halloween” to “Friday the 13th”, “SAW”, and\r\n“A Nightmare on Elm Street” all faced issues with moving the series along\r\nyet trying to stay true to the original film that inspired them and in\r\nevery case; failing to fully capture what made the original film a hit.\r\n\r\n“Evil Dead Rise” is not only the latest entry into the series but a bold\r\ntake on the cinematic series as it abandons the remote cabin setting of\r\nthe first two films and the reboot in favor of an urban setting.\r\n\r\nThe film does open in a remote lakeside locale before jumping a day\r\nearlier to California where estranged sisters Ellie (Alyssa Sutherland)\r\nand Beth (Lilly Sullivan) reunite. Beth has been working as a guitar\r\ntechnician and bristles that her sister refers to her as a groupie.\r\n\r\nEllie is raising three children after their father left and informs her\r\nsister that the building they are living in is scheduled to be demolished\r\nso they will have to move soon which is a shock to Beth as she has learned\r\nthat she is pregnant and is trying to figure out her life all the while\r\nseeing what she thought would be a source of stability for her upended.\r\n\r\nThe arrival of an earthquake allows access to a hidden area in the parking\r\ngarage of their building and since the locale was a former bank; the kids\r\nsoon find a hidden book and records which the only son Danny (Morgan\r\nDavies) hopes he can sell despite his sister Bridget (Gabrielle Echols)\r\nadmonishing him constantly to leave it alone and return it to where it was\r\nfound.\r\n\r\nThe book turns out to be a Necronomicon or “Book of the Dead” and as fans\r\nof the series know; nothing good ever comes from one and thanks to playing\r\nold records left by a Priest describing his thoughts on the book; a\r\nsummoning incantation is read and this causes Ellie to become possessed\r\nand unleash grotesque carnage and terror on her family.\r\n\r\nWhat follows is a gory and at times intense game of cat and mouse\r\npunctuated by moments of levity as the demonic infestation knows no end.\r\n\r\nThe film has the over-the top- gore that is expected of the series and the\r\nmakeup and effects are very effective. Writer? Director Lee Cronin has done\r\na great job capturing the tone of the series while moving it forward as he\r\ncleverly incorporates lines and nods to the series without making them\r\nseem forced.\r\n\r\nSome may find the excess of blood too much but the series has always been\r\nknown for taking things to the extreme as the blend of horror and comedic\r\nparody is what made the original film such a success.\r\n\r\nIn the end “Evil Dead Rise” does enough to keep fans of the series happy\r\nand hopefully, we will be seeing a new entry in the series in the future.\r\n\r\n3.5 stars out of 5",
                            created_at = "2023-04-22T16:12:33.817Z",
                            id = "64440771cee2f667e736c55d",
                            updated_at = "2023-04-22T16:12:33.888Z",
                            url = "https://www.themoviedb.org/review/64440771cee2f667e736c55d"
                        ),
                        MovieReview.Result(
                            author = "GenerationofSwine",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "",
                                username = "GenerationofSwine",
                                avatar_path = "/xYhvrFNntgAowjRsf6mRg9JgITr.jpg",
                                rating = 1
                            ),
                            content = "If you are expecting an Evil Dead movie... well, I guess it depends on what generation you're from.  If you are expecting Evil Dead, Evil Dead 2, Army of Darkness, the films of the unholy trilogy... you are going to be gravely disappointed.  This isn't Sam Raimi's Evil Dead.\r\n\r\nThis is, well, this is the Conjuring just like almost every other Horror movie past 2014.\r\n\r\nIt doesn't really breathe \"new life\" into the franchise, instead it takes the Evil Dead and breathes the PG-13 Neo-Horror Movie breathe into a franchise that used to be a heck of a lot of campy immature fun.  \r\n\r\nHere you get the same as everything else.",
                            created_at = "2023-04-24T00:01:42.572Z",
                            id = "6445c6e60582244d833294e8",
                            updated_at = "2023-04-24T00:01:42.664Z",
                            url = "https://www.themoviedb.org/review/6445c6e60582244d833294e8"
                        ),
                        MovieReview.Result(
                            author = "Chris Sawin",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "Chris Sawin",
                                username = "ChrisSawin",
                                avatar_path = "/https://secure.gravatar.com/avatar/bf3b87ecb40599290d764e6d73c86319.jpg",
                                rating = 7
                            ),
                            content = "_Evil Dead Rise_ is mostly entertaining as a new entry of the _Evil Dead_ franchise, but it’s got some hiccups that should be ironed out in any potential sequel.\r\n\r\nThe cast’s performances are all strong, with Sutherland masterfully making the most of her character with spine-tingling body language and unnerving dialogue, but as a fifth film in a well-known horror franchise, it’s disappointing to see that its most memorable moments are throwbacks to previous films.\r\n\r\nUltimately, _Evil Dead Rise_’s biggest flaw is that it simply isn’t as good as its ten-year-old predecessor. Nevertheless, the film is fun, blood splattering carnage that will hopefully take the _Evil Dead_ franchise in a bold and exhilarating direction.\r\n\r\nFull review: https://boundingintocomics.com/2023/05/02/evil-dead-rise-review-new-mommy-demon-look-same-great-chainsaw-taste/",
                            created_at = "2023-05-03T21:08:46.409Z",
                            id = "6452cd5e87a27a011b13329d",
                            updated_at = "2023-05-03T21:08:46.496Z",
                            url = "https://www.themoviedb.org/review/6452cd5e87a27a011b13329d"
                        ),
                        MovieReview.Result(
                            author = "CinemaSerf",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "CinemaSerf",
                                username = "Geronimo1967",
                                avatar_path = "/1kks3YnVkpyQxzw36CObFPvhL5f.jpg",
                                rating = 6
                            ),
                            content = "This is certainly a triumph for the visual and audio effects folks. Otherwise, I found it all a bit derivative and frankly a rather classless rehash of the much more entertaining original. It's tough to just keep on re-inventing these themes - they do tire, and as I reckon with the recent updates of the \"Halloween\" franchise, they maybe just rely too heavily on a current generation who were not around to see the originals in the cinema first time round, and who maybe just don't appreciate that the acting and the writing - though never exactly crucial to these plots - did have more of a role than just relying on well made-up (virtual) demons spinning around the ceiling emitting threatening shrieks. It's not terrible - it moves along quickly for ninety minutes and might just put you off a bath anytime shortly afterwards, but there just isn't any real sense of menace or jeopardy and to be honest I wasn't entirely sure that the family weren't nightmarish enough - before their visitor arrived. Perfectly watchable on the telly around Halloween with a drink in your hand, and it's a sort of gift that keeps on giving because you'll be able to watch it again the next year without the faintest recollection of what is going to happen!",
                            created_at = "2023-05-16T12:25:17.923Z",
                            id = "6463762def8b3200e3aeaae2",
                            updated_at = "2023-05-18T09:21:45.642Z",
                            url = "https://www.themoviedb.org/review/6463762def8b3200e3aeaae2"
                        ),
                        MovieReview.Result(
                            author = "Nathan",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "Nathan",
                                username = "TitanGusang",
                                avatar_path = "/yHGV91jVzmqpFOtRSHF0avBZmPm.jpg",
                                rating = 8
                            ),
                            content = "Evil Dead Rise puts a creative spin on the classic franchise while also remaining faithful to the core experience.\r\n\r\nThe story is not overly complex and admittedly somewhat generic, as the main characters find the Book of the Dead, read it, and all hell breaks loose. But the conditions surrounding this plot are what truly set it apart. For one, the high-rise setting was brilliant and created a very claustrophobic environment that worked really well. I found the earthquake to be an original idea not only for the discovery of The Necronomicon but also for the entrapment of our main characters.\r\n\r\nSpeaking of our cast, this film does a really great job of setting up these characters. I genuinely liked all of them, which made it difficult to see some of them go, unlike in Evil Dead (2013). The familiar bond between them was palpable, and this could not have been done without a fantastic cast. Alyssa Sutherland is by far my favorite Deadite in the entire franchise. Her motherly connection was haunting, her facial expressions, and mannerisms are nightmare fuel. Lily Sullivan was genuinely fantastic as a final girl. Her badass nature to defend the children is incredible, and she had some really great moments to stand out in the film. The kids overall did fine, nothing that was too amazing but nothing that took away from the film either.\r\n\r\nThe direction was my favorite part of the film by far. Lee Cronin did such a fantastic job with haunting camera work with subtle tilts and use of reflections. The action was top-notch, and the practical effects were amazing. So many scenes had me physically wincing with some insanely creative violence mixed with stomach-churning blood and wound effects. It is definitely a worthy successor to Evil Dead (2013) in the gore department.\r\n\r\nOverall, this may not be the best in the franchise, but I think it is one of the most refined. The story lacks in some areas, but the direction, performances, and violence really cement this as one of the best horror films of the year.\r\n\r\nScore: 84%  ✅\r\nVerdict: Great",
                            created_at = "2023-05-16T15:55:43.944Z",
                            id = "6463a77f8c44b9780961d349",
                            updated_at = "2023-05-16T15:55:43.995Z",
                            url = "https://www.themoviedb.org/review/6463a77f8c44b9780961d349"
                        ),
                        MovieReview.Result(
                            author = "Best SEO Services",
                            author_details = MovieReview.Result.AuthorDetails(
                                name = "Best SEO Services",
                                username = "techrj",
                                avatar_path = "/https://secure.gravatar.com/avatar/0050f2c6b32a780a767d4cf8350e85a6.jpg",
                                rating = null
                            ),
                            content = "**\"Evil Dead Rise\"** (2023) is a captivating and bone-chilling horror film that left me on the edge of my seat. From the opening scene to the final credits, the movie delivers a relentless onslaught of terror and gore. The practical effects are outstanding, creating some truly gruesome and memorable moments that will haunt your nightmares. The performances are top-notch, with the cast fully committed to their roles and delivering intense and believable performances. The direction by Lee Cronin is masterful, as he expertly builds tension and suspense throughout the film. The setting of an urban high-rise adds a unique and claustrophobic atmosphere, amplifying the sense of dread. \"Evil Dead Rise\" is a must-watch for horror aficionados who crave a thrilling and visceral experience. Brace yourself for a wild ride that will leave you both terrified and exhilarated.",
                            created_at = "2023-05-17T06:02:44.237Z",
                            id = "64646e040284200170064da7",
                            updated_at = "2023-05-17T23:03:28.802Z",
                            url = "https://www.themoviedb.org/review/64646e040284200170064da7"
                        )
                    ),
                    total_results = 6,
                    total_pages = 1
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: MovieReview = result.data!!
                        assertEquals(expected.results?.size, actual.results?.size)
                        for (i in 0 until expected.results?.size!!) {
                            val expectedReviewResults = expected.results!![i]
                            val actualReviewResults = actual.results!![i]

                            assertEquals(expectedReviewResults, actualReviewResults)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `shouldn't fetch movie reviews given 401 response`() {
        mockWebServer.enqueueResponse("error-response-401.json", 401)

        runBlocking {
            reviewsRepository.getMovieReview(
                713704,
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

    @Test
    fun `should fetch movie trailer correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-movie-trailer-response-200.json", 200)

        runBlocking {
            movieVideosRepository.getMovieVideos(713704).collectLatest { result ->
                val expected = MoviesVideos(
                    id = 713704,
                    results = listOf(
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "60 Second UK Home Entertainment Trailer",
                            key = "OWAqy24KqRY",
                            site = "YouTube",
                            size = 1080,
                            type = "Teaser",
                            official = true,
                            published_at = "2023-05-24T16:10:15.000Z",
                            id = "6479e522cf4b8b01227546d2"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Extended Preview",
                            key = "2wc5VpRc450",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-05-09T10:48:50.000Z",
                            id = "645a7b2d1b70ae0166be1f34"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Opening Title Sequence",
                            key = "k1LGAmQqA2A",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-04-29T08:30:21.000Z",
                            id = "644cff1a51a64e2640dd64b5"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Good Girl",
                            key = "C-iWk4c9LAo",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-04-28T08:00:07.000Z",
                            id = "644b9983596a9105a557c9a8"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "STUDIOCANAL PRESENTS: THE PODCAST - Episode 12 - Evil Dead, with Evil Dead Rise director Lee Cronin",
                            key = "eembXtBIerU",
                            site = "YouTube",
                            size = 1080,
                            type = "Featurette",
                            official = true,
                            published_at = "2023-04-27T17:30:06.000Z",
                            id = "645a37046aa8e00139bc0ba2"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Dead by Dawn",
                            key = "rfYmxSC1HrE",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-04-27T08:00:32.000Z",
                            id = "644a40fea39d0b0cf52469b1"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Burning Alive",
                            key = "tEyYxqHmsko",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-04-26T08:47:34.000Z",
                            id = "6448f0657f2d4a053b0b36da"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Beautiful Dream",
                            key = "V1-l4pMhGJw",
                            site = "YouTube",
                            size = 1080,
                            type = "Clip",
                            official = true,
                            published_at = "2023-04-25T17:04:00.000Z",
                            id = "64482b23f04d0104e697ed7e"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "EVIL DEAD RISE takes horror to a whole new bloody level",
                            key = "0lmBDnliqKs",
                            site = "YouTube",
                            size = 1080,
                            type = "Featurette",
                            official = true,
                            published_at = "2023-04-23T08:00:02.000Z",
                            id = "64451f7d05822404e933495b"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Audiences Spot",
                            key = "HuIyYNGM6Eg",
                            site = "YouTube",
                            size = 1080,
                            type = "Teaser",
                            official = true,
                            published_at = "2023-04-21T08:24:29.000Z",
                            id = "6442f30acee2f602fb3630c9"
                        ),
                        MoviesVideos.Result(
                            iso_639_1 = "en",
                            iso_3166_1 = "US",
                            name = "Lift prank at Evil Dead Rise screening",
                            key = "ajEm1_xD_kc",
                            site = "YouTube",
                            size = 1080,
                            type = "Featurette",
                            official = true,
                            published_at = "2023-04-18T16:08:21.000Z",
                            id = "643f329037b3a9049942b9d7"
                        )
                    )
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: MoviesVideos = result.data!!
                        assertEquals(expected.results.size, actual.results.size)
                        for (i in 0 until expected.results.size) {
                            val expectedTrailerResults = expected.results[i]
                            val actualTrailerResults = actual.results[i]

                            assertEquals(expectedTrailerResults, actualTrailerResults)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    @Test
    fun `shouldn't fetch movie trailer given 401 response`() {
        mockWebServer.enqueueResponse("error-response-401.json", 401)

        runBlocking {
            movieVideosRepository.getMovieVideos(
                713704
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
