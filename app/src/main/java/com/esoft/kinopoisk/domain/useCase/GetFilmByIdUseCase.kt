package com.esoft.kinopoisk.domain.useCase

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.repository.FilmsRepository

class GetFilmByIdUseCase(private val filmsRepository: FilmsRepository) {

    fun getFilmById(id: Int): Film =
        filmsRepository.getFilmById(id = id)

}