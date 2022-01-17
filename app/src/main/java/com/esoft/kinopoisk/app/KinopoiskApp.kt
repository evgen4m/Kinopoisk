package com.esoft.kinopoisk.app

import android.app.Application
import com.esoft.kinopoisk.data.FilmRepositoryDataSourceImp
import com.esoft.kinopoisk.data.FilmsApi
import com.esoft.kinopoisk.data.repository.FilmRepositoryImp
import com.esoft.kinopoisk.domain.repository.FilmsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KinopoiskApp: Application(){

    var filmsRepository: FilmsRepository? = null

    private val BASE_URL = "https://s3-eu-west-1.amazonaws.com"
    private var mRetrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        filmsRepository = FilmRepositoryImp(FilmRepositoryDataSourceImp(mRetrofit!!.create(FilmsApi::class.java)))

    }

}