package id.web.dedekurniawan.moviexplorer.person.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import id.web.dedekurniawan.moviexplorer.core.utils.Date

@Parcelize
class Person(
    val id: Int,
    val name: String,
    val originalName: String? = null,
    val profilePath: String? = null,
    val knownFor: List<String>,
    val alsoKnownAs: List<String>? = null,
    val gender: String,
    val mediaType: String? = null,
    val knownForDepartment: String? = null,
    val popularity: Float? = null,
    val biography: String? = null,
    val placeOfBirth: String? = null,
    val birthday: Date? = null,
    val deathday: Date? = null,
    val imdbId: String? = null,
    val homepage: String? = null,
    val adult: Boolean
    ): Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false
        if (originalName != other.originalName) return false
        if (profilePath != other.profilePath) return false
        if (knownFor != other.knownFor) return false
        if (alsoKnownAs != other.alsoKnownAs) return false
        if (gender != other.gender) return false
        if (mediaType != other.mediaType) return false
        if (knownForDepartment != other.knownForDepartment) return false
        if (popularity != other.popularity) return false
        if (biography != other.biography) return false
        if (placeOfBirth != other.placeOfBirth) return false
        if (birthday != other.birthday) return false
        if (deathday != other.deathday) return false
        if (imdbId != other.imdbId) return false
        if (homepage != other.homepage) return false
        if (adult != other.adult) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (originalName?.hashCode() ?: 0)
        result = 31 * result + (profilePath?.hashCode() ?: 0)
        result = 31 * result + knownFor.hashCode()
        result = 31 * result + (alsoKnownAs?.hashCode() ?: 0)
        result = 31 * result + gender.hashCode()
        result = 31 * result + (mediaType?.hashCode() ?: 0)
        result = 31 * result + knownForDepartment.hashCode()
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (biography?.hashCode() ?: 0)
        result = 31 * result + (placeOfBirth?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (deathday?.hashCode() ?: 0)
        result = 31 * result + (imdbId?.hashCode() ?: 0)
        result = 31 * result + (homepage?.hashCode() ?: 0)
        result = 31 * result + adult.hashCode()
        return result
    }
}