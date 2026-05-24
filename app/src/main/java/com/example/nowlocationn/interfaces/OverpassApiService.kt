package com.example.nowlocationn.interfaces

import com.example.nowlocationn.model.OverpassResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassApiService {
    @FormUrlEncoded // Esto le dice que enviamos un formulario
    @POST("api/interpreter")
    suspend fun buscarPlanes(
        @Field("data") query: String // Usamos @Field en lugar de @Query
    ): OverpassResponse
}