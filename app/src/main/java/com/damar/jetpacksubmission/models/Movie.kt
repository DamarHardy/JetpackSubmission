package com.damar.jetpacksubmission.models
data class Movie(
	var id: Int,
	var title: String,
	var adult: Boolean,
	var video: Boolean,
	var voteCount: Int,
	var category: String,
	var overview: String,
	var posterPath: String,
	var popularity: Double,
	var voteAverage: Double,
	var releaseDate: String,
	var backdropPath: String,
	var originalTitle: String,
	var originalLanguage: String
)
