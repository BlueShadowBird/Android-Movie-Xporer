package id.web.dedekurniawan.moviexplorer.movie.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieVideoApiResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: List<MovieVideoResponseItem>
)

data class MovieVideoResponseItem(

	@field:SerializedName("site")
	val site: String,

	@field:SerializedName("size")
	val size: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("official")
	val official: Boolean,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("key")
	val key: String
)
