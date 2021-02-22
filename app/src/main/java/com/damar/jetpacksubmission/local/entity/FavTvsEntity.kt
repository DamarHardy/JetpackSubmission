package com.damar.jetpacksubmission.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteTvs", indices = [Index(value = ["name"], unique = true)])
data class FavTvsEntity(
    var name: String,
    @PrimaryKey var id: Int,
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