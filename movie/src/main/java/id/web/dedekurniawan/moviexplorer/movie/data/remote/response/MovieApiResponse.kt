package id.web.dedekurniawan.moviexplorer.movie.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieApiResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<MovieResponse?>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class MovieResponse(

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("title")
	val title: String,

    @field:SerializedName("original_title")
	val originalTitle: String,

    @field:SerializedName("tagline")
	val tagline: String? = null,

    @field:SerializedName("overview")
	val overview: String,

    @field:SerializedName("homepage")
	val homepage: String? = null,

    @field:SerializedName("poster_path")
	val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

    @field:SerializedName("vote_average")
	val voteAverage: Float? = null,

    @field:SerializedName("vote_count")
	val voteCount: Int? = null,

    @field:SerializedName("popularity")
	val popularity: Float? = null,

    @field:SerializedName("release_date")
	val releaseDate: String? = null,

    @field:SerializedName("original_language")
	val originalLanguage: String? = null,

    @field:SerializedName("status")
	val status: String? = null,

    @field:SerializedName("video")
	val video: Boolean? = null,

    @field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

    @field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

    @field:SerializedName("adult")
	val adult: Boolean? = null,

    @field:SerializedName("imdb_id")
	val imdbId: String? = null,

    @field:SerializedName("budget")
	val budget: Long? = null,

    @field:SerializedName("revenue")
	val revenue: Long? = null,

    @field:SerializedName("runtime")
	val runtime: Int? = null,

    @field:SerializedName("belongs_to_collection")
	val belongsToCollection: BelongsToCollection? = null,

    @field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

    @field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

    @field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem?>? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
)

data class SpokenLanguagesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("english_name")
	val englishName: String? = null
)

data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class BelongsToCollection(

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
)
