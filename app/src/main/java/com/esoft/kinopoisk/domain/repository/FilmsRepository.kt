package com.esoft.kinopoisk.domain.repository

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres

interface FilmsRepository {

    fun getAllFilms(callback: (List<Film>) -> Unit)

    fun getGenresInFilms(callback: (Set<Genres>) -> Unit)

    fun getFilmById(id: Int, callback: (Film) -> Unit)
}