package com.esoft.kinopoisk.presentation.detailFilmFragment

import com.esoft.kinopoisk.domain.useCase.GetFilmByIdUseCase
import com.esoft.kinopoisk.presentation.base.BasePresenter

class DetailFilmPresenter(
    private val id: Int,
    private val getFilmByIdUseCase: GetFilmByIdUseCase
    ): BasePresenter<DetailFilmView>() {

        fun getFilmById() {
            getFilmByIdUseCase.getFilmById(id = id) {
                view?.getFilmById(film = it)
            }
        }

}