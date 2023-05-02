package id.web.dedekurniawan.moviexplorer.core.data.remote.retrofit

import id.web.dedekurniawan.moviexplorer.core.data.remote.response.MovieResponse
import id.web.dedekurniawan.moviexplorer.core.data.remote.response.MovieSearchResponse
import id.web.dedekurniawan.moviexplorer.core.enum.Sort
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sort: Sort = Sort.Vote.DESCENDING
    ): MovieSearchResponse

    @GET("movie/{movieid}")
    suspend fun retrieveMovie(
        @Path("movieid") movieid: Int,
    ): MovieResponse
}