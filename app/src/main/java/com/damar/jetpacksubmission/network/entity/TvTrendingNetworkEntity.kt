package com.damar.jetpacksubmission.network.entity

import com.squareup.moshi.Json

data class TvsTrendingNetworkEntity(

	@Json(name="page")
	val page: Int? = null,

	@Json(name="total_pages")
	val totalPages: Int? = null,

	@Json(name="results")
	val results: List<TvTrendingNetworkEntity>? = null,

	@Json(name="total_results")
	val totalResults: Int? = null
)
data class TvTrendingNetworkEntity(

	@Json(name="first_air_date")
	var firstAirDate: String = "",

	@Json(name="overview")
	var overview: String? = null,

	@Json(name="original_language")
	var originalLanguage: String? = null,

	@Json(name="genre_ids")
	var genreIds: List<Int?>? = null,

	@Json(name="poster_path")
	var posterPath: String? = null,

	@Json(name="origin_country")
	var originCountry: List<String>? = null,

	@Json(name="backdrop_path")
	var backdropPath: String? = null,

	@Json(name="media_type")
	var mediaType: String? = null,

	@Json(name="vote_average")
	var voteAverage: Double? = null,

	@Json(name="original_name")
	var originalName: String? = null,

	@Json(name="popularity")
	var popularity: Double? = null,

	@Json(name="name")
	var name: String = "",

	@Json(name="id")
	var id: Int? = null,

	@Json(name="vote_count")
	var voteCount: Int? = null
)
