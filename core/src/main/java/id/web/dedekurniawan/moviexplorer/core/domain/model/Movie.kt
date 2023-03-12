package id.web.dedekurniawan.moviexplorer.core.domain.model

import android.os.Parcelable
import id.web.dedekurniawan.moviexplorer.core.utils.Date
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val tagline: String? = null,
    val overview: String,
    val homepage: String? = null,
    val posterPath: String?,
    val voteAverage: Float?,
    val voteCount: Int? = null,
    val popularity: Float?,
    val releaseDate: Date?,
    val original_language: String?,
    val status: String? = null,
    val imdbId: String? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val genreList: List<String>? = null,


): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (originalTitle != other.originalTitle) return false
        if (overview != other.overview) return false
        if (voteAverage != other.voteAverage) return false
        if (popularity != other.popularity) return false
        if (releaseDate != other.releaseDate) return false
        if (original_language != other.original_language) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + (voteAverage?.hashCode() ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (original_language?.hashCode() ?: 0)
        return result
    }
}