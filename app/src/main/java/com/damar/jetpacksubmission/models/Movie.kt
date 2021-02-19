package com.damar.jetpacksubmission.models
data class Movie(
	var overview: String,
	var originalLanguage: String,
	var originalTitle: String,
	var video: Boolean,
	var title: String,
	var posterPath: String,
	var backdropPath: String,
	var releaseDate: String,
	var popularity: Double,
	var voteAverage: Double,
	var id: Int,
	var adult: Boolean,
	var voteCount: Int,
	var category: String
)
