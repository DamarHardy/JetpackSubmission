package com.damar.jetpacksubmission.local

import androidx.room.PrimaryKey
import com.damar.jetpacksubmission.local.entity.FavMoviesEntity
import com.damar.jetpacksubmission.local.entity.FavTvsEntity
import com.damar.jetpacksubmission.local.entity.MoviesCacheEntity
import com.damar.jetpacksubmission.local.entity.TvsCacheEntity
import com.damar.jetpacksubmission.models.DetailMv
import com.damar.jetpacksubmission.models.DetailTv
import com.damar.jetpacksubmission.models.Movie
import com.damar.jetpacksubmission.models.Tv
import com.damar.jetpacksubmission.network.NO_INFO
import com.damar.jetpacksubmission.utils.EntityMapper

object CacheMapperTv: EntityMapper<TvsCacheEntity, Tv> {
    override fun mapFromEntity(entity: TvsCacheEntity): Tv {
        return Tv(
                firstAirDate = entity.firstAirDate,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                originCountry = mutableListOf(),
                popularity = entity.popularity,
                voteAverage = entity.voteAverage,
                originalName = entity.originalName,
                name = entity.name,
                id = entity.id,
                voteCount = entity.voteCount,
                category = entity.category
        )
    }

    override fun mapToEntity(domainModel: Tv): TvsCacheEntity {
        return TvsCacheEntity(
                id = domainModel.id,
                name = domainModel.name,
                overview = domainModel.overview,
                category = domainModel.category,
                voteCount = domainModel.voteCount,
                posterPath = domainModel.posterPath,
                popularity = domainModel.popularity,
                voteAverage = domainModel.voteAverage,
                originalName = domainModel.originalName,
                backdropPath = domainModel.backdropPath,
                firstAirDate = domainModel.firstAirDate,
                originalLanguage = domainModel.originalLanguage
        )
    }

    fun mapFromEntityList(entities: List<TvsCacheEntity>): List<Tv>{
        return entities.map { mapFromEntity(it) }
    }
    fun mapToEntityList(entities: List<Tv>): List<TvsCacheEntity>{
        return entities.map { mapToEntity(it) }
    }
}
object CacheMapperMovie: EntityMapper<MoviesCacheEntity, Movie> {
    override fun mapFromEntity(entity: MoviesCacheEntity): Movie {
        return Movie(
                overview = entity.overview,
                originalLanguage = entity.originalLanguage ,
                originalTitle = entity.originalTitle ,
                video = entity.video ,
                title = entity.title ,
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                releaseDate = entity.releaseDate,
                voteAverage = entity.voteAverage,
                popularity = entity.popularity,
                id = entity.id ,
                adult = entity.adult ,
                voteCount = entity.voteCount,
                category = entity.category
        )
    }

    override fun mapToEntity(domainModel: Movie): MoviesCacheEntity {
        return MoviesCacheEntity(
                overview = domainModel.overview,
                originalLanguage = domainModel.originalLanguage ,
                originalTitle = domainModel.originalTitle ,
                video = domainModel.video ,
                title = domainModel.title ,
                posterPath = domainModel.posterPath,
                backdropPath = domainModel.backdropPath,
                releaseDate = domainModel.releaseDate,
                voteAverage = domainModel.voteAverage,
                popularity = domainModel.popularity,
                id = domainModel.id ,
                adult = domainModel.adult ,
                voteCount = domainModel.voteCount,
                category = domainModel.category
        )
    }

    fun mapFromEntityList(entities: List<MoviesCacheEntity>): List<Movie>{
        return entities.map { mapFromEntity(it) }
    }
    fun mapToEntityList(entities: List<Movie>): List<MoviesCacheEntity>{
        return entities.map{ mapToEntity(it)}
    }
}
object CacheMapperMovieFav: EntityMapper<FavMoviesEntity, Movie> {
    override fun mapFromEntity(entity: FavMoviesEntity): Movie {
        return Movie(
            overview = entity.overview,
            originalLanguage = entity.originalLanguage ,
            originalTitle = entity.originalTitle ,
            video = entity.video ,
            title = entity.title ,
            posterPath = entity.posterPath,
            backdropPath = entity.backdropPath,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage,
            popularity = entity.popularity,
            id = entity.id ,
            adult = entity.adult ,
            voteCount = entity.voteCount,
            category = entity.category
        )
    }

    override fun mapToEntity(domainModel: Movie): FavMoviesEntity {
        return FavMoviesEntity(
            overview = domainModel.overview,
            originalLanguage = domainModel.originalLanguage ,
            originalTitle = domainModel.originalTitle ,
            video = domainModel.video ,
            title = domainModel.title ,
            posterPath = domainModel.posterPath,
            backdropPath = domainModel.backdropPath,
            releaseDate = domainModel.releaseDate,
            voteAverage = domainModel.voteAverage,
            popularity = domainModel.popularity,
            id = domainModel.id ,
            adult = domainModel.adult ,
            voteCount = domainModel.voteCount,
            category = domainModel.category
        )
    }
}
object CacheMapperTvFav: EntityMapper<FavTvsEntity, Tv> {
    override fun mapFromEntity(entity: FavTvsEntity): Tv {
        return Tv(
                firstAirDate = entity.firstAirDate,
                overview = entity.overview ,
                originalLanguage = entity.originalLanguage ,
                posterPath = entity.posterPath ,
                originCountry = mutableListOf(),
                backdropPath = entity.backdropPath,
                popularity = entity.popularity,
                voteAverage = entity.voteAverage,
                originalName = entity.originalName,
                name = entity.name,
                id = entity.id,
                voteCount = entity.voteCount,
                category = entity.category
        )
    }

    override fun mapToEntity(domainModel: Tv): FavTvsEntity {
        return FavTvsEntity(
                id = domainModel.id,
                name = domainModel.name,
                category = domainModel.category,
                overview = domainModel.overview,
                voteCount = domainModel.voteCount,
                posterPath = domainModel.posterPath,
                popularity = domainModel.popularity,
                voteAverage = domainModel.voteAverage,
                originalName = domainModel.originalName,
                backdropPath = domainModel.backdropPath,
                firstAirDate = domainModel.firstAirDate,
                originalLanguage = domainModel.originalLanguage
        )
    }
}
object CacheMapperDetailMovieFav{
    fun mapToEntity(domainModel: DetailMv): FavMoviesEntity {
        return FavMoviesEntity(
        title =  domainModel.title,
        id =  domainModel.id,
        video =  domainModel.video,
        adult =  domainModel.adult,
        voteCount =  domainModel.voteCount,
        category =  NO_INFO,
        overview =  domainModel.overview,
        popularity =  domainModel.popularity,
        posterPath =  domainModel.posterPath,
        releaseDate =  domainModel.releaseDate,
        voteAverage =  domainModel.voteAverage,
        backdropPath =  domainModel.backdropPath,
        originalTitle =  domainModel.originalTitle,
        originalLanguage =  domainModel.originalLanguage
        )
    }
}
object CacheMapperDetailTvFav{
    fun mapToEntity(domainModel: DetailTv): FavTvsEntity {
        return FavTvsEntity(
                name = domainModel.name,
                id = domainModel.id,
                voteCount = domainModel.voteCount,
                overview = domainModel.overview,
                category = "TV",
                posterPath = domainModel.posterPath,
                popularity = domainModel.popularity,
                voteAverage = domainModel.voteAverage,
                backdropPath = domainModel.backdropPath,
                originalName = domainModel.originalName,
                firstAirDate = domainModel.firstAirDate,
                originalLanguage = domainModel.originalLanguage,
                )
    }
}