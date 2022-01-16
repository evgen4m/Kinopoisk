package com.esoft.kinopoisk.presentation.listFragment

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.presentation.base.BaseView

interface ListFilmsView: BaseView {

    fun getAllFilms(list: ArrayList<Film>)

    fun getGenres(set: HashSet<Genres>)

    fun openDetailScreen(id: Int)
}