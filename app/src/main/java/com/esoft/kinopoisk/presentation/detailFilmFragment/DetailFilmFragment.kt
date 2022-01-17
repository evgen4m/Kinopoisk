package com.esoft.kinopoisk.presentation.detailFilmFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.app.KinopoiskApp
import com.esoft.kinopoisk.databinding.FragmentDetailFilmBinding
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.useCase.GetFilmByIdUseCase

class DetailFilmFragment : Fragment(R.layout.fragment_detail_film), DetailFilmView {

    private lateinit var binding: FragmentDetailFilmBinding

    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        val getFilmByIdUseCase = GetFilmByIdUseCase(filmsRepository = repository)
        DetailFilmPresenter(
            id = requireArguments().getInt("filmId"),
            getFilmByIdUseCase = getFilmByIdUseCase
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailFilmBinding.bind(view)
        presenter.attachView(this)
        presenter.getFilmById()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    override fun getFilmById(film: Film) {
        Glide
            .with(requireContext())
            .load(film.image_url)
            .placeholder(R.drawable.ic_not_found)
            .into(binding.imageDetailFilm)

        binding.textDetailName.text = film.name
        binding.textDetailDate.text = "Год: ${film.year}"
        binding.textDetailRating.text = "Рейтинг: ${film.rating?:"Неизвестно"}"
        binding.textDetailDescription.text = film.description?: "Описание отсутствует"
    }

}