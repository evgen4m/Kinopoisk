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

    private var _binding: FragmentDetailFilmBinding? = null
    private val binding get() = _binding!!

    private companion object {
        const val filmId = "filmId"
    }

    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        val getFilmByIdUseCase = GetFilmByIdUseCase(filmsRepository = repository)
        DetailFilmPresenter(
            id = requireArguments().getInt(filmId),
            getFilmByIdUseCase = getFilmByIdUseCase
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailFilmBinding.bind(view)
        presenter.attachView(this)
        presenter.getFilmById()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getFilmById(film: Film) {

        binding.apply {
            Glide
                .with(requireContext())
                .load(film.image_url)
                .placeholder(R.drawable.ic_not_found)
                .into(imageDetailFilm)

            textDetailName.text = film.name
            textDetailDate.text = getString(R.string.textReleaseDate) + film.year
            textDetailRating.text = getString(R.string.rating) + film.year?: getString(R.string.unknown)
            textDetailDescription.text = film.description?:getString(R.string.no_description)
        }

    }

}