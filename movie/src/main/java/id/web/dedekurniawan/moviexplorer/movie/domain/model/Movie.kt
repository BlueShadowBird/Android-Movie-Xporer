package id.web.dedekurniawan.moviexplorer.movie.domain.model

import android.os.Parcelable
import id.web.dedekurniawan.moviexplorer.core.utils.Date
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String? = null,
    val tagline: String? = null,
    val overview: String,
    val homepage: String? = null,
    val posterPath: String?,
    val voteAverage: Float? = null,
    val voteCount: Int? = null,
    val popularity: Float? = null,
    val releaseDate: Date? = null,
    val originalLanguage: String? = null,
    val status: String? = null,
    val imdbId: String? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val genreList: List<String>? = null,
    var isFavorite: Boolean = false

    ): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (originalTitle != other.originalTitle) return false
        if (tagline != other.tagline) return false
        if (overview != other.overview) return false
        if (homepage != other.homepage) return false
        if (posterPath != other.posterPath) return false
        if (voteAverage != other.voteAverage) return false
        if (voteCount != other.voteCount) return false
        if (popularity != other.popularity) return false
        if (releaseDate != other.releaseDate) return false
        if (originalLanguage != other.originalLanguage) return false
        if (status != other.status) return false
        if (imdbId != other.imdbId) return false
        if (budget != other.budget) return false
        if (revenue != other.revenue) return false
        if (runtime != other.runtime) return false
        if (genreList != other.genreList) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + (tagline?.hashCode() ?: 0)
        result = 31 * result + overview.hashCode()
        result = 31 * result + (homepage?.hashCode() ?: 0)
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (voteAverage?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (imdbId?.hashCode() ?: 0)
        result = 31 * result + (budget?.hashCode() ?: 0)
        result = 31 * result + (revenue?.hashCode() ?: 0)
        result = 31 * result + (runtime ?: 0)
        result = 31 * result + (genreList?.hashCode() ?: 0)
        result = 31 * result + isFavorite.hashCode()
        return result
    }
}