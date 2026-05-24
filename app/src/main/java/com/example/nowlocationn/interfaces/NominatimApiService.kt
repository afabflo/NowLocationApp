package com.example.nowlocationn.interfaces

import com.example.nowlocationn.model.NominatimDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimApiService {
    @GET("search")
    suspend fun  buscarCiudad(
        @Query("q") ciudad :String,
        @Query("format") format: String  = "json",
        @Query("limit") limit:Int = 1,
        @Query("countrycodes") countrycodes :String = "es"
    ) : List<NominatimDto>
}