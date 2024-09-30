package id.web.dedekurniawan.moviexplorer.person.data.remote.response

import com.google.gson.annotations.SerializedName

data class PersonApiResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<PersonResponse>?,

	@field:SerializedName("total_results")
	val totalResults: Int?
)

data class KnownForItem(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("video")
	val video: Boolean,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("media_type")
	val mediaType: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("popularity")
	val popularity: Any,

	@field:SerializedName("vote_average")
	val voteAverage: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("vote_count")
	val voteCount: Int,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("origin_country")
	val originCountry: List<String>,

	@field:SerializedName("original_name")
	val originalName: String,

	@field:SerializedName("name")
	val name: String
)

data class PersonResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("original_name")
	val originalName: String?,

	@field:SerializedName("also_known_as")
	val alsoKnownAs: List<String>? = null,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("media_type")
	val mediaType: String?,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String,

	@field:SerializedName("known_for")
	val knownFor: List<KnownForItem>,

	@field:SerializedName("popularity")
	val popularity: Float?,

	@field:SerializedName("profile_path")
	val profilePath: String?,

	@field:SerializedName("biography")
	val biography: String? = null,

	@field:SerializedName("birthday")
	val birthday: String?,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("deathday")
	val deathday: String? = null,

	@field:SerializedName("place_of_birth")
	val placeOfBirth: String? = null,

	@field:SerializedName("homepage")
	val homepage: String? = null,

	@field:SerializedName("adult")
	val adult: Boolean
)
