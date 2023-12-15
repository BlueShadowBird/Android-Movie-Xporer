package id.web.dedekurniawan.moviexplorer.movie.data.remote

import id.web.dedekurniawan.moviexplorer.core.enum.Sort
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
}