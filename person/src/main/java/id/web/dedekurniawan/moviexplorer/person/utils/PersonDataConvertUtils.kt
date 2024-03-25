package id.web.dedekurniawan.moviexplorer.person.utils

import id.web.dedekurniawan.moviexplorer.core.utils.toDate
import id.web.dedekurniawan.moviexplorer.person.data.remote.PersonResponse
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.domain.model.PersonQuickSearchModel

fun PersonResponse.toDomainModel() = Person(
    id,
    name,
    originalName,
    profilePath,
    listOf(),
    alsoKnownAs,
    when(gender){
        1 -> "Female"
        2 -> "Male"
        else -> "Not Specified"
    },
    mediaType,
    knownForDepartment,
    popularity,
    biography,
    placeOfBirth,
    birthday?.toDate(),
    deathday?.toDate(),
    imdbId,
    homepage,
    adult
)

fun PersonResponse.toQuickSearchModel() = PersonQuickSearchModel(
    id.toString(),
    name,
    knownForDepartment
)