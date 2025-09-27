package com.example.spirala1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("plants/search")
    suspend fun pretrazi(
        @Query("q") query: String,
        @Query("token") token: String
    ): Response<PretragaResponse>

    @GET("plants/{plantId}")
    suspend fun biljkaDetails(
        @Path("plantId") plantId: String,
        @Query("token") token: String
    ): Response<Pretraga>

    @GET("plants/search")
    suspend fun bojaCvijeta(
        @Query("filter[flower_color]") flowerColor: String,
        @Query("q") query: String,
        @Query("token") token: String,
        @Query("page") page: Int
    ): Response<PretragaResponse>
}
