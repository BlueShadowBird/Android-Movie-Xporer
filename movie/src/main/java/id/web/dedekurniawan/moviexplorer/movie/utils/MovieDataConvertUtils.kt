package id.web.dedekurniawan.moviexplorer.movie.utils

import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.core.utils.Date
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieImageResponseItem
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieResponse
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieVideoResponseItem
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.model.MovieQuickSearchModel

fun MovieResponse.toDomainModel() = Movie(
    id,
    title,
    originalTitle,
    tagline,
    overview,
    homepage,
    backdropPath ?: posterPath,
    voteAverage,
    voteCount,
    popularity,
    releaseDate?.let {
        if (it.isNotEmpty()) {
            Date().apply {
                parseDate(it)
            }
        } else null
    },
    originalLanguage,
    status,
    imdbId,
    budget,
    revenue,
    runtime,
    genres?.map { it?.name.toString() }
)

fun MovieResponse.toQuickSearchModel() = MovieQuickSearchModel(
    id.toString(),
    originalTitle,
    tagline.toString()
)

fun MovieImageResponseItem.toMediaSourceInfo() = MediaSourceInfo(filePath)

fun MovieVideoResponseItem.toMediaSourceInfo() = MediaSourceInfo(
    key,
    if("YouTube" == site)MediaSourceInfo.MEDIA_SOURCE_YOUTUBE else MediaSourceInfo.MEDIA_SOURCE_VIDEO,
    name
)