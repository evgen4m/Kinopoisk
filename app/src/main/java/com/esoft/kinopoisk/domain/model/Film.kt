package com.esoft.kinopoisk.domain.model

data class Film (
    val id: Int?,
    val localized_name: String?,
    val name: String?,
    val year: String?,
    val rating: String?,
    val image_url: String?,
    val description: String?,
    val genres: List<String>?
)