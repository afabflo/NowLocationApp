package com.example.nowlocationn.di

import android.content.Context
import androidx.room.Room
import com.example.nowlocationn.data.local.AppDatabase
import com.example.nowlocationn.data.local.FavoritoDao
import com.example.nowlocationn.data.local.HistorialDao
import com.example.nowlocationn.interfaces.GeoApiService
import com.example.nowlocationn.interfaces.NominatimApiService
import com.example.nowlocationn.interfaces.OverpassApiService
import com.example.nowlocationn.interfaces.TicketmasterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("Github")
    fun provideGithubRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("Nominatim")
    fun provideNominatimRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "NowLocationn/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder().header("User-Agent","NowLocationApp").build()
            chain.proceed(request)
        }.build()
        return Retrofit.Builder().baseUrl("https://nominatim.openstreetmap.org/").client(client).
                addConverterFactory(GsonConverterFactory.create()).build()
    }
    */

    @Provides
    @Singleton
    @Named("Overpass")
    fun provideOverpassRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "NowLocationn/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://overpass-api.de/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideGeoApiService(
        @Named("Github") retrofit: Retrofit
    ): GeoApiService {
        return retrofit.create(GeoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNominatimApiService(
        @Named("Nominatim") retrofit: Retrofit
    ): NominatimApiService {
        return retrofit.create(NominatimApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOverpassApiService(
        @Named("Overpass") retrofit: Retrofit
    ): OverpassApiService {
        return retrofit.create(OverpassApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nowlocation_db"
        )
            .fallbackToDestructiveMigration()
            .build()    }
    @Provides
    @Singleton
    fun provideFavoritoDao(db: AppDatabase): FavoritoDao {
        return db.favoritoDao()
    }
    @Provides
    @Singleton
    @Named("Ticketmaster")
    fun provideTicketmasterRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://app.ticketmaster.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTicketmasterApiService(
        @Named("Ticketmaster") retrofit: Retrofit
    ): TicketmasterApiService {
        return retrofit.create(TicketmasterApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideHistorialDao(db: AppDatabase): HistorialDao {
        return db.historialDao()
    }

}