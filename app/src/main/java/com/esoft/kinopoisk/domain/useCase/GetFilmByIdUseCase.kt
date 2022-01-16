package com.esoft.kinopoisk.domain.useCase

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.repository.FilmsRepository

class GetFilmByIdUseCase(private val filmsRepository: FilmsRepository) {

    fun getFilmById(id: Int, callback: (Film) -> Unit) =
        filmsRepository.getFilmById(id = id, callback = callback)

}