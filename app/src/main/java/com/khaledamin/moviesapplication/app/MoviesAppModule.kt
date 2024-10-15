package com.khaledamin.moviesapplication.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.khaledamin.moviesapplication.BuildConfig
import com.khaledamin.moviesapplication.data.local.MoviesDB
import com.khaledamin.moviesapplication.data.local.MoviesDao
import com.khaledamin.moviesapplication.data.repository.MoviesRepoImpl
import com.khaledamin.moviesapplication.data.remote.MoviesApi
import com.khaledamin.moviesapplication.data.remote.NetworkState
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesAppModule : Application() {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory)
            .client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor {
        val request = it.request().newBuilder().addHeader(
            "Authorization",
            BuildConfig.API_KEY
        ).build()
        it.proceed(request)
    }.build()

    @Provides
    @Singleton
    fun provideMoviesDB(@ApplicationContext context: Context): MoviesDB =
        Room.databaseBuilder(
            context.applicationContext,
            MoviesDB::class.java,
            "movies_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(moviesDB: MoviesDB): MoviesDao = moviesDB.moviesDao()

    @Provides
    @Singleton
    fun provideRepo(api: MoviesApi, dao: MoviesDao, networkUtil: NetworkState): MoviesRepo =
        MoviesRepoImpl(api, dao, networkUtil)

    @Provides
    @Singleton
    fun provideNetworkUtil(@ApplicationContext context: Context): NetworkState =
        NetworkState(context)

}
