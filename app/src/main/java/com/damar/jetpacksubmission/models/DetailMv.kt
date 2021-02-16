package com.damar.jetpacksubmission.models

import com.squareup.moshi.Json

data class DetailMv(

	@Json(name="original_language")
	val originalLanguage: String? = null,

	@Json(name="imdb_id")
	val imdbId: String? = null,

	@Json(name="video")
	val video: Boolean? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="backdrop_path")
	val backdropPath: String? = null,

	@Json(name="revenue")
	val revenue: Int? = null,

	@Json(name="genres")
	val genres: List<GenresItem?>? = null,

	@Json(name="popularity")
	val popularity: Double? = null,

	@Json(name="production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="vote_count")
	val voteCount: Int? = null,

	@Json(name="budget")
	val budget: Int? = null,

	@Json(name="overview")
	val overview: String? = null,

	@Json(name="images")
	val images: Images? = null,

	@Json(name="original_title")
	val originalTitle: String? = null,

	@Json(name="runtime")
	val runtime: Int? = null,

	@Json(name="poster_path")
	val posterPath: String? = null,

	@Json(name="spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

	@Json(name="production_companies")
	val productionCompanies: List<ProductionCompaniesItem?>? = null,

	@Json(name="release_date")
	val releaseDate: String? = null,

	@Json(name="vote_average")
	val voteAverage: Double? = null,

	@Json(name="belongs_to_collection")
	val belongsToCollection: Any? = null,

	@Json(name="tagline")
	val tagline: String? = null,

	@Json(name="adult")
	val adult: Boolean? = null,

	@Json(name="homepage")
	val homepage: String? = null,

	@Json(name="status")
	val status: String? = null
)

data class PostersItem(

	@Json(name="aspect_ratio")
	val aspectRatio: Double? = null,

	@Json(name="file_path")
	val filePath: String? = null,

	@Json(name="vote_average")
	val voteAverage: Double? = null,

	@Json(name="width")
	val width: Int? = null,

	@Json(name="iso_639_1")
	val iso6391: String? = null,

	@Json(name="vote_count")
	val voteCount: Int? = null,

	@Json(name="height")
	val height: Int? = null
)

data class GenresItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null
)

data class ProductionCompaniesItem(

	@Json(name="logo_path")
	val logoPath: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="origin_country")
	val originCountry: String? = null
)

data class ProductionCountriesItem(

	@Json(name="iso_3166_1")
	val iso31661: String? = null,

	@Json(name="name")
	val name: String? = null
)

data class BackdropsItem(

	@Json(name="aspect_ratio")
	val aspectRatio: Double? = null,

	@Json(name="file_path")
	val filePath: String? = null,

	@Json(name="vote_average")
	val voteAverage: Double? = null,

	@Json(name="width")
	val width: Int? = null,

	@Json(name="iso_639_1")
	val iso6391: String? = null,

	@Json(name="vote_count")
	val voteCount: Int? = null,

	@Json(name="height")
	val height: Int? = null
)
