package id.web.dedekurniawan.moviexplorer.person.data.remote

import id.web.dedekurniawan.moviexplorer.core.enum.Sort
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApiService {
    @GET("trending/person/day")
    suspend fun retrievePeople(): PersonApiResponse

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sort: Sort = Sort.Vote.DESCENDING
    ): PersonApiResponse

    @GET("person/{personid}")
    suspend fun retrievePerson(
        @Path("personid") personid: Int,
    ): PersonResponse
}