package com.mirvan.moviedb.App_feature.domain.model

import com.mirvan.moviedb.App_feature.presentation.detailmovie.DetailMovieState

data class MovieDetail(
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    data class BelongsToCollection(
        val backdrop_path: String?,
        val id: Int?,
        val name: String?,
        val poster_path: String?
    )

    data class Genre(
        val id: Int?,
        val name: String?
    )

    data class ProductionCompany(
        val id: Int?,
        val logo_path: String?,
        val name: String?,
        val origin_country: String?
    )

    data class ProductionCountry(
        val iso_3166_1: String?,
        val name: String?
    )

    data class SpokenLanguage(
        val english_name: String?,
        val iso_639_1: String?,
        val name: String?
    )

    fun toMovieDetailState(): DetailMovieState {
        val genresDetail: MutableList<Genre> = mutableListOf()
        genres?.map {
            genresDetail.add(
                Genre(
                    id = it.id,
                    name = it.name
                )
            )
        }
        val belongsToCollectionDetail = BelongsToCollection(
            backdrop_path = belongs_to_collection?.backdrop_path,
            id = belongs_to_collection?.id,
            name = belongs_to_collection?.name,
            poster_path = belongs_to_collection?.poster_path
        )
        val productionCompaniesDetail: MutableList<ProductionCompany> = mutableListOf()
        production_companies?.map {
            productionCompaniesDetail.add(
                ProductionCompany(
                    id = it.id,
                    name = it.name,
                    logo_path = it.logo_path,
                    origin_country = it.origin_country
                )
            )
        }
        val productionCountriesDetail: MutableList<ProductionCountry> = mutableListOf()
        production_countries?.map {
            productionCountriesDetail.add(
                ProductionCountry(
                    iso_3166_1 = it.iso_3166_1,
                    name = it.name
                )
            )
        }
        val spokenLanguageDetail: MutableList<SpokenLanguage> = mutableListOf()
        spoken_languages?.map {
            spokenLanguageDetail.add(
                SpokenLanguage(
                    name = it.name,
                    english_name = it.english_name,
                    iso_639_1 = it.iso_639_1
                )
            )
        }
        return DetailMovieState(
            movieDetail = MovieDetail(
                adult = adult,
                backdrop_path = backdrop_path,
                belongs_to_collection = belongsToCollectionDetail,
                budget = budget,
                genres = genresDetail,
                homepage = homepage,
                id = id,
                imdb_id = imdb_id,
                original_language = original_language,
                original_title = original_title,
                overview = overview,
                popularity = popularity,
                poster_path = poster_path,
                production_companies = productionCompaniesDetail,
                production_countries = productionCountriesDetail,
                release_date = release_date,
                revenue = revenue,
                runtime = runtime,
                spoken_languages = spokenLanguageDetail,
                status = status,
                tagline = tagline,
                title = title,
                video = video,
                vote_average = vote_average,
                vote_count = vote_count
            )
        )
    }
}
