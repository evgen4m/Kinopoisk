package com.esoft.kinopoisk.data

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres

interface FilmRepositoryDataSource {

  fun getAllFilms(callback: (List<Film>) -> Unit)

  fun getFilmGenres(callback: (Set<Genres>) -> Unit)

  fun getFilmById(id: Int, callback: (Film) -> Unit)

}