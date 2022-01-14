package com.esoft.kinopoisk.data

import com.esoft.kinopoisk.domain.model.Results
import retrofit2.Call
import retrofit2.http.GET

interface FilmsApi {

    @GET("/sequeniatesttask/films.json")
    fun getAllFilms(): Call<Results>?

}