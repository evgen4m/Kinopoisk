package com.esoft.kinopoisk.presentation.base

open class BasePresenter<V : BaseView> {

    var view: V? = null

    fun attachView(view: V) {
        this.view = view
        onAttachView()
    }

    open fun onAttachView () {

    }

    fun destroy() {
        view = null
    }

}