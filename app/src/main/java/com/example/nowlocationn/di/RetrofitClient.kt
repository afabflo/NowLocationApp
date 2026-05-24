package com.example.nowlocationn.di
/*
import com.example.nowlocationn.interfaces.GeoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "https://api-de-municipios.com"
    val instance : GeoApiService by lazy{
        //Retrofit actua como cliente http para gestionar el transporte
        //Y gson deserializa el json para convertirlo a kotlin
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Usamos Gson
            .build().create(GeoApiService::class.java) //Especificamos que queremos que  retrofit esta escrito en java

    }
}*/