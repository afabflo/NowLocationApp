package com.example.nowlocationn.interfaces

import com.example.nowlocationn.model.TicketmasterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketmasterApiService {
    @GET("discovery/v2/events.json")
    suspend fun buscarEventos(
        @Query("apikey") apiKey: String,
        @Query("countryCode") countryCode: String = "ES",
        @Query("city") ciudad: String,
        @Query("size") size: Int = 10,
        @Query("sort") sort: String = "date,asc",
        @Query("startDateTime") startDateTime: String,
        @Query("endDateTime") endDateTime: String
    ): TicketmasterResponse
}