package id.web.dedekurniawan.moviexplorer.movie.utils

import id.web.dedekurniawan.moviexplorer.core.utils.Date
import id.web.dedekurniawan.moviexplorer.movie.data.remote.MovieResponse
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