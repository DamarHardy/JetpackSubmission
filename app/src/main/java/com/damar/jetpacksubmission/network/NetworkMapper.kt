package com.damar.jetpacksubmission.network

import com.damar.jetpacksubmission.models.*
import com.damar.jetpacksubmission.models.BackdropsItem
import com.damar.jetpacksubmission.models.CreatedByItem
import com.damar.jetpacksubmission.models.GenresItem
import com.damar.jetpacksubmission.models.Images
import com.damar.jetpacksubmission.models.NetworksItem
import com.damar.jetpacksubmission.models.PostersItem
import com.damar.jetpacksubmission.models.ProductionCompaniesItem
import com.damar.jetpacksubmission.models.ProductionCountriesItem
import com.damar.jetpacksubmission.models.SeasonsItem
import com.damar.jetpacksubmission.models.SpokenLanguagesItem
import com.damar.jetpacksubmission.network.entity.*
import com.damar.jetpacksubmission.utils.EntityMapper

const val NO_INFO = "No Information"
object NetworkMapperMvTrending: EntityMapper<MvTrendingNetworkEntity, Movie> {
    override fun mapFromEntity(entity: MvTrendingNetworkEntity): Movie {
        return Movie(
                overview = entity.overview ?: NO_INFO,
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                originalTitle = entity.originalTitle ?: NO_INFO,
                video = entity.video ?: false,
                title = entity.title ?: NO_INFO,
                posterPath = entity.posterPath ?: "",
                backdropPath = entity.backdropPath ?: "",
                releaseDate = entity.releaseDate ?: NO_INFO,
                voteAverage = entity.voteAverage ?: 0.0,
                popularity = entity.popularity ?: 0.0,
                id = entity.id ?: 0,
                adult = entity.adult ?: false,
                voteCount = entity.voteCount ?: 0,
                category = "Trending"
        )
    }

    override fun mapToEntity(domainModel: Movie): MvTrendingNetworkEntity {
        return MvTrendingNetworkEntity(
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
                voteCount = domainModel.voteCount
        )
    }

    fun mapFromEntityList(entities: List<MvTrendingNetworkEntity>): List<Movie>{
        return entities.map { mapFromEntity(it) }
    }
}
object NetworkMapperMvPopular: EntityMapper<MvPopularNetworkEntity, Movie> {
    override fun mapFromEntity(entity: MvPopularNetworkEntity): Movie {
        return Movie(
                overview = entity.overview ?: NO_INFO,
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                originalTitle = entity.originalTitle ?: NO_INFO,
                video = entity.video ?: false,
                title = entity.title,
                posterPath = entity.posterPath ?: "",
                backdropPath = entity.backdropPath ?: "",
                releaseDate = entity.releaseDate ?: NO_INFO,
                voteAverage = entity.voteAverage ?: 0.0,
                popularity = entity.popularity ?: 0.0,
                id = entity.id ?: 0,
                adult = entity.adult ?: false,
                voteCount = entity.voteCount ?: 0,
                category = "Popular"
        )
    }

    override fun mapToEntity(domainModel: Movie): MvPopularNetworkEntity {
        return MvPopularNetworkEntity(
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
                voteCount = domainModel.voteCount
        )
    }

    fun mapFromEntityList(entities: List<MvPopularNetworkEntity>): List<Movie>{
        return entities.map { mapFromEntity(it) }
    }
}
object NetworkMapperTvPopular: EntityMapper<TvPopularNetworkEntity, Tv> {
    override fun mapFromEntity(entity: TvPopularNetworkEntity): Tv {
        return Tv(
                firstAirDate = entity.firstAirDate ?: NO_INFO,
                overview = entity.overview ?: NO_INFO,
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                posterPath = entity.posterPath ?: "",
                backdropPath = entity.backdropPath ?: "",
                originCountry = entity.originCountry ?: mutableListOf(),
                popularity = entity.popularity ?: 0.0,
                voteAverage = entity.voteAverage ?: 0.0,
                originalName = entity.originalName ?: NO_INFO,
                name = entity.name,
                id = entity.id ?: 0,
                voteCount = entity.voteCount ?: 0,
                category = "Popular"
        )
    }

    override fun mapToEntity(domainModel: Tv): TvPopularNetworkEntity {
        return TvPopularNetworkEntity(
                firstAirDate = domainModel.firstAirDate,
                overview = domainModel.overview,
                originalLanguage = domainModel.originalLanguage,
                posterPath = domainModel.posterPath,
                backdropPath = domainModel.backdropPath,
                originCountry = domainModel.originCountry,
                popularity = domainModel.popularity,
                voteAverage = domainModel.voteAverage,
                originalName = domainModel.originalName,
                name = domainModel.name,
                id = domainModel.id,
                voteCount = domainModel.voteCount
        )
    }

    fun mapFromEntityList(entities: List<TvPopularNetworkEntity>): List<Tv>{
        return entities.map { mapFromEntity(it) }
    }
}
object NetworkMapperTvTrending: EntityMapper<TvTrendingNetworkEntity, Tv> {
    override fun mapFromEntity(entity: TvTrendingNetworkEntity): Tv {
        return Tv(
                firstAirDate = entity.firstAirDate,
                overview = entity.overview ?: NO_INFO,
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                posterPath = entity.posterPath ?: "",
                backdropPath = entity.backdropPath ?: "",
                originCountry = entity.originCountry ?: mutableListOf(),
                popularity = entity.popularity ?: 0.0,
                voteAverage = entity.voteAverage ?: 0.0,
                originalName = entity.originalName ?: NO_INFO,
                name = entity.name,
                id = entity.id ?: 0,
                voteCount = entity.voteCount ?: 0,
                category = "Trending"
        )
    }

    override fun mapToEntity(domainModel: Tv): TvTrendingNetworkEntity {
        return TvTrendingNetworkEntity(
                firstAirDate = domainModel.firstAirDate,
                overview = domainModel.overview,
                originalLanguage = domainModel.originalLanguage,
                posterPath = domainModel.posterPath,
                backdropPath = domainModel.backdropPath,
                originCountry = domainModel.originCountry,
                popularity = domainModel.popularity,
                voteAverage = domainModel.voteAverage,
                originalName = domainModel.originalName,
                name = domainModel.name,
                id = domainModel.id,
                voteCount = domainModel.voteCount
        )
    }

    fun mapFromEntityList(entities: List<TvTrendingNetworkEntity>): List<Tv>{
        return entities.map { mapFromEntity(it) }
    }
}
object NetworkMapperTvDetail: EntityMapper<DetailTvNetworkEntity, DetailTv>{
    override fun mapFromEntity(entity: DetailTvNetworkEntity): DetailTv {
        val networks = entity.networks?.map {
            NetworksItem(
                    logoPath = it.logoPath ?: NO_INFO,
                    name = it.name ?: NO_INFO,
                    id = it.id ?: 0,
                    originCountry = it.originCountry ?: NO_INFO
            )
        }  ?: mutableListOf()
        val genres = entity.genres?.map {
            GenresItem(
                    name = it.name ?: NO_INFO,
                    id = it.id ?: 0
            )
        } ?: mutableListOf()
        val productionCountries = entity.productionCountries?.map {
            ProductionCountriesItem(
                    iso31661 = it.iso31661 ?: NO_INFO,
                    name = it.name ?: NO_INFO
            )
        }  ?: mutableListOf()
        val seasons = entity.seasons?.map {
            SeasonsItem(
                airDate = it.airDate ?: NO_INFO,
                overview = it.overview ?: NO_INFO,
                episodeCount = it.episodeCount ?: 0,
                name = it.name ?: NO_INFO,
                seasonNumber = it.seasonNumber ?: 0,
                id = it.id ?: 0,
                posterPath = it.posterPath ?: NO_INFO ,

            )
        }  ?: mutableListOf()
        val images = Images(
                backdrops = entity.images.backdrops?.map {
                    BackdropsItem(
                            aspectRatio = it.aspectRatio ?: 0.0,
                            filePath = it.filePath ?: NO_INFO,
                            voteAverage = it.voteAverage  ?: 0.0,
                            width = it.width ?: 0,
                            iso6391 = it.iso6391 ?: NO_INFO,
                            voteCount = it.voteCount ?: 0,
                            height = it.height ?: 0,
                    )
                } ?: mutableListOf(),
                posters = entity.images.posters?.map {
                    PostersItem(
                            aspectRatio = it.aspectRatio ?: 0.0,
                            filePath = it.filePath ?: NO_INFO,
                            voteAverage = it.voteAverage  ?: 0.0,
                            width = it.width ?: 0,
                            iso6391 = it.iso6391 ?: NO_INFO,
                            voteCount = it.voteCount ?: 0,
                            height = it.height ?: 0,
                    )
                } ?: mutableListOf()
        )
        val createdBy = entity.createdBy?.map{
            CreatedByItem(
                    gender = it.gender ?: 0,
                    creditId = it.creditId ?: NO_INFO ,
                    name = it.name ?: NO_INFO ,
                    profilePath = it.profilePath ?: NO_INFO ,
                    id = it.id ?: 0
            )
        } ?: mutableListOf()
        val spokenLanguage = entity.spokenLanguages?.map {
            SpokenLanguagesItem(
                    name=it.name ?: NO_INFO,
                    iso6391=it.iso6391 ?: NO_INFO,
                    englishName=it.englishName ?: NO_INFO
            )
        } ?: mutableListOf()
        val productionCompanies = entity.productionCompanies?.map {
            ProductionCompaniesItem(
                    logoPath=it.logoPath ?: NO_INFO,
                    name=it.name ?: NO_INFO,
                    id=it.id ?: 0,
                    originCountry=it.originCountry ?: NO_INFO
            )
        } ?: mutableListOf()
        return DetailTv(
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                numberOfEpisodes = entity.numberOfEpisodes ?: 0,
                networks = networks,
                type = entity.type ?: NO_INFO,
                backdropPath = entity.backdropPath ?: "",
                genres = genres,
                popularity = entity.popularity ?: 0.0,
                productionCountries = productionCountries,
                id = entity.id ?: 0,
                numberOfSeasons = entity.numberOfSeasons ?: 0,
                voteCount = entity.voteCount ?: 0,
                firstAirDate = entity.firstAirDate ?: NO_INFO,
                overview = entity.overview ?: NO_INFO,
                seasons = seasons,
                images = images,
                languages = entity.languages ?: mutableListOf(),
                createdBy = createdBy,
                posterPath = entity.posterPath ?: "",
                originCountry = entity.originCountry ?: mutableListOf(),
                spokenLanguages = spokenLanguage,
                productionCompanies = productionCompanies,
                originalName = entity.originalName ?: NO_INFO,
                voteAverage = entity.voteAverage ?: 0.0,
                name = entity.name ?: NO_INFO,
                tagLine = entity.tagLine ?: NO_INFO,
                episodeRunTime = entity.episodeRunTime ?: mutableListOf(),
                inProduction = entity.inProduction ?: false,
                lastAirDate = entity.lastAirDate ?: NO_INFO,
                homepage = entity.homepage ?: NO_INFO,
                status = entity.status ?: NO_INFO,
        )
    }

    override fun mapToEntity(domainModel: DetailTv): DetailTvNetworkEntity {
        return DetailTvNetworkEntity(images = ImagesNetwork(
                backdrops = mutableListOf(),
                posters =  mutableListOf()
        ))
    }

}
object NetworkMapperMvDetail: EntityMapper<DetailMvNetworkEntity, DetailMv>{
    override fun mapFromEntity(entity: DetailMvNetworkEntity): DetailMv {
        val genres = entity.genres?.map {
            GenresItem(
                    name = it.name ?: NO_INFO,
                    id = it.id ?: 0
            )
        } ?: mutableListOf()
        val productionCountries = entity.productionCountries?.map {
            ProductionCountriesItem(
                    iso31661 = it.iso31661 ?: NO_INFO,
                    name = it.name ?: NO_INFO
            )
        }  ?: mutableListOf()
        val images = Images(
                backdrops = entity.images.backdrops?.map {
                    BackdropsItem(
                            aspectRatio = it.aspectRatio ?: 0.0,
                            filePath = it.filePath ?: NO_INFO,
                            voteAverage = it.voteAverage  ?: 0.0,
                            width = it.width ?: 0,
                            iso6391 = it.iso6391 ?: NO_INFO,
                            voteCount = it.voteCount ?: 0,
                            height = it.height ?: 0,
                    )
                } ?: mutableListOf(),
                posters = entity.images.posters?.map {
                    PostersItem(
                            aspectRatio = it.aspectRatio ?: 0.0,
                            filePath = it.filePath ?: NO_INFO,
                            voteAverage = it.voteAverage  ?: 0.0,
                            width = it.width ?: 0,
                            iso6391 = it.iso6391 ?: NO_INFO,
                            voteCount = it.voteCount ?: 0,
                            height = it.height ?: 0,
                    )
                } ?: mutableListOf()
        )
        val spokenLanguage = entity.spokenLanguages?.map {
            SpokenLanguagesItem(
                    name=it.name ?: NO_INFO,
                    iso6391=it.iso6391 ?: NO_INFO,
                    englishName=it.englishName ?: NO_INFO
            )
        } ?: mutableListOf()
        val productionCompanies = entity.productionCompanies?.map {
            ProductionCompaniesItem(
                    logoPath=it.logoPath ?: NO_INFO,
                    name=it.name ?: NO_INFO,
                    id=it.id ?: 0,
                    originCountry=it.originCountry ?: NO_INFO
            )
        } ?: mutableListOf()
        val belongToCollection = BelongToCollection(
                id = entity.belongsToCollection?.id ?: 0,
                name = entity.belongsToCollection?.name ?: NO_INFO,
                posterPath = entity.belongsToCollection?.posterPath ?: "",
                backdropPath = entity.belongsToCollection?.backdropPath ?: ""
        )
        return DetailMv(
                originalLanguage = entity.originalLanguage ?: NO_INFO,
                imdbId = entity.imdbId ?: NO_INFO,
                video = entity.video ?: false,
                title = entity.title ?: NO_INFO,
                backdropPath = entity.backdropPath ?: "",
                genres = genres,
                popularity = entity.popularity ?: 0.0,
                productionCountries = productionCountries,
                id = entity.id ?: 0,
                voteCount = entity.voteCount ?: 0,
                budget = entity.budget ?: 0,
                overview = entity.overview ?: NO_INFO,
                images = images,
                originalTitle = entity.originalTitle ?: NO_INFO,
                runtime = entity.runtime ?: 0,
                posterPath = entity.posterPath ?: "",
                spokenLanguages = spokenLanguage,
                productionCompanies = productionCompanies,
                releaseDate = entity.releaseDate ?: NO_INFO,
                voteAverage = entity.voteAverage ?: 0.0,
                belongsToCollection = belongToCollection,
                tagLine = entity.tagline ?: NO_INFO,
                adult = entity.adult ?: false,
                homepage = entity.homepage ?: NO_INFO,
                status = entity.status ?: NO_INFO,
                revenue = entity.revenue ?: 0
        )
    }

    override fun mapToEntity(domainModel: DetailMv): DetailMvNetworkEntity {
        return DetailMvNetworkEntity( images = ImagesNetwork(
                backdrops = mutableListOf(),
                posters =  mutableListOf()
        ))
    }
}
