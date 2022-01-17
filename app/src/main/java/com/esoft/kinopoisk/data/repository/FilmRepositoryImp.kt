package com.esoft.kinopoisk.data.repository

import com.esoft.kinopoisk.data.FilmRepositoryDataSource
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.domain.repository.FilmsRepository

class FilmRepositoryImp(private val filmsRepositoryDataSource: FilmRepositoryDataSource): FilmsRepository {

    override fun getAllFilms(callback: (List<Film>) -> Unit) =
        filmsRepositoryDataSource.getAllFilms(callback = callback)

    override fun getGenresInFilms(callback: (Set<Genres>) -> Unit) =
        filmsRepositoryDataSource.getFilmGenres(callback = callback)

    override fun getFilmById(id: Int): Film =
        filmsRepositoryDataSource.getFilmById(id = id)
}