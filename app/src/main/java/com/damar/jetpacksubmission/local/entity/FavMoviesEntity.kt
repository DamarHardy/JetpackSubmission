package com.damar.jetpacksubmission.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovies", indices = [Index(value = ["id"], unique = true)])
data class FavMoviesEntity(
    var title: String,
    @PrimaryKey var id: Int,
    var video: Boolean,
    var adult: Boolean,
    var voteCount: Int,
    var category: String,
    var overview: String,
    var popularity: Double,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    var backdropPath: String,
    var originalTitle: String,
    var originalLanguage: String
)