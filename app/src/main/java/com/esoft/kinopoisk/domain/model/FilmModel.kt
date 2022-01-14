package com.esoft.kinopoisk.domain.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("films")
    val list: List<FilmModel>
)

data class FilmModel(

    val title: String = "Фильмы",

    @SerializedName("id")
    val id: Int,
    @SerializedName("localized_name")
    val localized_name: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("genres")
    val genres: List<String>
)