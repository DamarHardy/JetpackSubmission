package com.damar.jetpacksubmission.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class MoviesTrendingMoshi(

	@Json(name="page")
	val page: Int? = null,

	@Json(name="total_pages")
	val totalPages: Int? = null,

	@Json(name="results")
	val results: List<MvTrending>? = null,

	@Json(name="total_results")
	val totalResults: Int? = null
)
@Entity(tableName = "MvTrending", indices = [Index(value = ["title"], unique = true)])
data class MvTrending(

	@Json(name="overview")
	var overview: String? = null,

	@Json(name="original_language")
	var originalLanguage: String? = null,

	@Json(name="original_title")
	var originalTitle: String? = null,

	@Json(name="video")
	var video: Boolean? = null,

	@PrimaryKey
	@Json(name="title")
	var title: String = "",

	@Json(name="genre_ids")
	@Ignore
	var genreIds: List<Int?>? = null,

	@Json(name="poster_path")
	var posterPath: String? = null,

	@Json(name="backdrop_path")
	var backdropPath: String? = null,

	@Json(name="release_date")
	var releaseDate: String? = null,

	@Json(name="media_type")
	var mediaType: String? = null,

	@Json(name="vote_average")
	var voteAverage: Double? = null,

	@Json(name="popularity")
	var popularity: Double? = null,

	@Json(name="id")
	var id: Int? = null,

	@Json(name="adult")
	var adult: Boolean? = null,

	@Json(name="vote_count")
	var voteCount: Int? = null
)
