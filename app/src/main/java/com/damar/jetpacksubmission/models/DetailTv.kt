package com.damar.jetpacksubmission.models

data class DetailTv(
	val originalLanguage: String,
	val numberOfEpisodes: Int,
	val networks: List<NetworksItem>,
	val type: String,
	val backdropPath: String,
	val genres: List<GenresItem>,
	val popularity: Double,
	val productionCountries: List<ProductionCountriesItem>,
	val id: Int,
	val numberOfSeasons: Int,
	val voteCount: Int,
	val firstAirDate: String,
	val overview: String,
	val seasons: List<SeasonsItem>,
	val images: Images,
	val languages: List<String>,
	val createdBy: List<CreatedByItem>,
	val posterPath: String,
	val originCountry: List<String>,
	val spokenLanguages: List<SpokenLanguagesItem>,
	val productionCompanies: List<ProductionCompaniesItem>,
	val originalName: String,
	val voteAverage: Double,
	val name: String,
	val tagLine: String,
	val episodeRunTime: List<Int>,
	val inProduction: Boolean,
	val lastAirDate: String,
	val homepage: String,
	val status: String
)

data class SeasonsItem(
	val airDate: String,
	val overview: String,
	val episodeCount: Int,
	val name: String,
	val seasonNumber: Int,
	val id: Int,
	val posterPath: String
)

data class NextEpisodeToAir(
	val productionCode: String,
	val airDate: String,
	val overview: String,
	val episodeNumber: Int,
	val voteAverage: Double,
	val name: String,
	val seasonNumber: Int,
	val id: Int,
	val stillPath: Any,
	val voteCount: Int
)
data class LastEpisodeToAir(
	val productionCode: String,
	val airDate: String,
	val overview: String,
	val episodeNumber: Int,
	val voteAverage: Double,
	val name: String,
	val seasonNumber: Int,
	val id: Int,
	val stillPath: String,
	val voteCount: Int
)

data class Images(
	val backdrops: List<BackdropsItem>,
	val posters: List<PostersItem>
)

data class NetworksItem(
	val logoPath: String,
	val name: String,
	val id: Int,
	val originCountry: String
)

data class CreatedByItem(
	val gender: Int,
	val creditId: String,
	val name: String,
	val profilePath: String,
	val id: Int
)

data class SpokenLanguagesItem(
	val name: String,
	val iso6391: String,
	val englishName: String
)
