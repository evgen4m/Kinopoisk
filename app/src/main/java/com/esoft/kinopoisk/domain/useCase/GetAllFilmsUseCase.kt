package com.esoft.kinopoisk.domain.useCase

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.repository.FilmsRepository

class GetAllFilmsUseCase(private val filmsRepository: FilmsRepository) {

    fun getAllFilms(callback: (List<Film>) -> Unit) =
        filmsRepository.getAllFilms(callback = callback)



}