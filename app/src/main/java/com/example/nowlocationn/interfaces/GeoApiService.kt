package com.example.nowlocationn.interfaces

import com.example.nowlocationn.model.MunicipioDto
import com.example.nowlocationn.model.NominatimDto
import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit
interface GeoApiService {
    @GET("frontid/ComunidadesProvinciasPoblaciones/master/poblaciones.json")
    suspend fun getMunicipios(): List<MunicipioDto>

    @GET("search")
    suspend fun buscarCordenadas(
        @Query("q") ciudad:String,
        @Query("format") format:String = "json"
    ): List<NominatimDto>
}