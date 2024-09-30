package id.web.dedekurniawan.moviexplorer.movie.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieImageApiResponse(

	@field:SerializedName("backdrops")
	val backdrops: List<MovieImageResponseItem>,

	@field:SerializedName("posters")
	val posters: List<MovieImageResponseItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("logos")
	val logos: List<MovieImageResponseItem>
)

data class MovieImageResponseItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Float,

	@field:SerializedName("file_path")
	val filePath: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("height")
	val height: Int
)