package com.esoft.kinopoisk.presentation.detailFilmFragment

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.app.KinopoiskApp
import com.esoft.kinopoisk.databinding.FragmentDetailFilmBinding
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.useCase.GetFilmByIdUseCase

class DetailFilmFragment : Fragment(R.layout.fragment_detail_film), DetailFilmView {

    private var _binding: FragmentDetailFilmBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null

    private companion object {
        const val filmId = "filmId"
    }

    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        val getFilmByIdUseCase = GetFilmByIdUseCase(filmsRepository = repository!!)
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

        val toolbar = binding.detailFragmentToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> {
                Toast.makeText(this.requireContext(),"ss", Toast.LENGTH_LONG).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun getFilmById(film: Film) {

        binding.apply {
            Glide
                .with(requireContext())
                .load(film.image_url)
                .placeholder(R.drawable.ic_not_found)
                .into(imageDetailFilm)

            textDetailName.text = film.name
            title = film.localized_name
            textDetailDate.text = getString(R.string.textReleaseDate, film.year)
            textDetailRating.text = getString(R.string.rating, film.rating?: getString(R.string.unknown))
            textDetailDescription.text = film.description?:getString(R.string.no_description)
        }

    }

}