package com.esoft.kinopoisk.presentation.detailFilmFragment

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.presentation.base.BaseView

interface DetailFilmView: BaseView {

    fun getFilmById(film: Film)

}