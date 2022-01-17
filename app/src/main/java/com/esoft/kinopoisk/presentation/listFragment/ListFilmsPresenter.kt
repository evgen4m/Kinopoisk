package com.esoft.kinopoisk.presentation.listFragment

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.domain.useCase.GetAllFilmsUseCase
import com.esoft.kinopoisk.domain.useCase.GetFilmGenresUseCase
import com.esoft.kinopoisk.presentation.base.BasePresenter

class ListFilmsPresenter(
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val getFilmGenresUseCase: GetFilmGenresUseCase
    ): BasePresenter<ListFilmsView>() {

    fun getAllFilms() {
       getAllFilmsUseCase.getAllFilms {
           view?.getAllFilms(it as ArrayList<Film>)
       }
    }

    fun getGenres() {
        getFilmGenresUseCase.getGenres {
            view?.getGenres(it)
        }
    }

    fun openDetailScreen(film: Film) {
        view?.openDetailScreen(id = film.id!!)
    }
}