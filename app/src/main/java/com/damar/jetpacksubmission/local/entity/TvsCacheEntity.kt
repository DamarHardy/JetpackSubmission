package com.damar.jetpacksubmission.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Tv", indices = [Index(value = ["name"], unique = true)])
data class TvsCacheEntity(
	@PrimaryKey	var name: String,
	var id: Int,
	var voteCount: Int,
	var overview: String,
	var category: String,
	var posterPath: String,
	var popularity: Double,
	var voteAverage: Double,
	var backdropPath: String,
	var originalName: String,
	var firstAirDate: String,
	var originalLanguage: String
)
