package com.esoft.kinopoisk.domain.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("films")
    val list: List<FilmModel>? = null
)

data class FilmModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("localized_name")
    val localized_name: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("year")
    val year: String? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("image_url")
    val image_url: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("genres")
    val genres: List<String>? = null
)