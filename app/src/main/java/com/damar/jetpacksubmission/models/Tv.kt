package com.damar.jetpacksubmission.models

data class Tv(
	var firstAirDate: String,
	var overview: String,
	var originalLanguage: String,
	var posterPath: String,
	var originCountry: List<String>,
	var backdropPath: String,
	var popularity: Double,
	var voteAverage: Double,
	var originalName: String,
	var name: String,
	var id: Int,
	var voteCount: Int,
	var category: String
)
