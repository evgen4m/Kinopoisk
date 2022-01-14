package com.esoft.kinopoisk.presentation.detailFilmFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.app.KinopoiskApp
import com.esoft.kinopoisk.databinding.FragmentDetailFilmBinding
import com.esoft.kinopoisk.domain.model.Film

class DetailFilmFragment : Fragment(R.layout.fragment_detail_film), DetailFilmView {

    private lateinit var binding: FragmentDetailFilmBinding

    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        DetailFilmPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailFilmBinding.bind(view)
    }

}