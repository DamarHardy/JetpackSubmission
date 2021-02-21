package com.damar.jetpacksubmission.models

data class DetailMv(
	val originalLanguage: String,
	val imdbId: String,
	val video: Boolean,
	val title: String,
	val backdropPath: String,
	val revenue: Int,
	val genres: List<GenresItem>,
	val popularity: Double,
	val productionCountries: List<ProductionCountriesItem>,
	val id: Int,
	val voteCount: Int,
	val budget: Int,
	val overview: String,
	val images: Images,
	val originalTitle: String,
	val runtime: Int,
	val posterPath: String,
	val spokenLanguages: List<SpokenLanguagesItem>,
	val productionCompanies: List<ProductionCompaniesItem>,
	val releaseDate: String,
	val voteAverage: Double,
	val belongsToCollection: BelongToCollection,
	val tagLine: String,
	val adult: Boolean,
	val homepage: String,
	val status: String
)

data class BelongToCollection(
	val id : Int? = null,
	val name : String? = null,
	val posterPath: String? = null,
	val backdropPath: String? = null
)

data class PostersItem(
	val aspectRatio: Double,
	val filePath: String,
	val voteAverage: Double,
	val width: Int,
	val iso6391: String,
	val voteCount: Int,
	val height: Int
)

data class GenresItem(
	val name: String,
	val id: Int
)

data class ProductionCompaniesItem(
	val logoPath: String,
	val name: String,
	val id: Int,
	val originCountry: String
)

data class ProductionCountriesItem(
	val iso31661: String,
	val name: String
)

data class BackdropsItem(
	val aspectRatio: Double,
	val filePath: String,
	val voteAverage: Double,
	val width: Int,
	val iso6391: String,
	val voteCount: Int,
	val height: Int
)
