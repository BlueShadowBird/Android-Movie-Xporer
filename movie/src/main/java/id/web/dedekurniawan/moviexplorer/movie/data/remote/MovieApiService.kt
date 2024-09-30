package id.web.dedekurniawan.moviexplorer.movie.data.remote

import id.web.dedekurniawan.moviexplorer.core.enum.Sort
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieApiResponse
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieImageApiResponse
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieResponse
import id.web.dedekurniawan.moviexplorer.movie.data.remote.response.MovieVideoApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun retrieveMovie(
    ): MovieApiResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sort: Sort = Sort.Vote.DESCENDING
    ): MovieApiResponse

    @GET("movie/{movieid}")
    suspend fun retrieveMovie(
        @Path("movieid") movieid: Int,
    ): MovieResponse

    @GET("movie/{movieid}/images")
    suspend fun retrieveMovieImages(
        @Path("movieid") movieid: Int,
    ): MovieImageApiResponse

    @GET("movie/{movieid}/videos")
    suspend fun retrieveMovieVideos(
        @Path("movieid") movieid: Int,
    ): MovieVideoApiResponse
}