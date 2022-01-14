package com.esoft.kinopoisk.domain.useCase

import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.domain.repository.FilmsRepository

class GetFilmGenresUseCase(private val filmsRepository: FilmsRepository) {

    fun getGenres(callback: (Set<Genres>) -> Unit) =
        filmsRepository.getGenresInFilms(callback = callback)

}