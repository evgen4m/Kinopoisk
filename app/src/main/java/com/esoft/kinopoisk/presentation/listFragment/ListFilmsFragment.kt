package com.esoft.kinopoisk.presentation.listFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.app.KinopoiskApp
import com.esoft.kinopoisk.databinding.FragmentListFilmsBinding
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.domain.useCase.GetAllFilmsUseCase
import com.esoft.kinopoisk.domain.useCase.GetFilmGenresUseCase


class ListFilmsFragment : Fragment(R.layout.fragment_list_films), ListFilmsView {

    private lateinit var binding: FragmentListFilmsBinding
    private lateinit var navController: NavController


    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        val getAllFilmsUseCase = GetAllFilmsUseCase(filmsRepository = repository)
        val getFilmGenresUseCase = GetFilmGenresUseCase(filmsRepository = repository)
        ListFilmsPresenter(
            getAllFilmsUseCase = getAllFilmsUseCase,
            getFilmGenresUseCase = getFilmGenresUseCase
        )
    }

    private val adapterFilms = ListFilmsAdapter {
        presenter.openDetailScreen(film = it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        presenter.getAllFilms()
        presenter.getGenres()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentListFilmsBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

    }

    override fun onResume() {
        super.onResume()
        binding.recyclerFilmList.apply {
            adapter = adapterFilms
            val manager = GridLayoutManager(activity, 2)
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterFilms.getItemViewType(position)) {
                        0 -> 2
                        1 -> 1
                        2 -> 2
                        else -> 2
                    }
                }
            }
            layoutManager = manager
        }
    }

    override fun getAllFilms(list: ArrayList<Film>) {
        adapterFilms.listFilms = list
    }

    override fun getGenres(set: HashSet<Genres>) {
        adapterFilms.setGenres = set
    }

    override fun openDetailScreen(id: Int) {
        val bundle = Bundle()
        bundle.putInt("filmId", id)
        navController.navigate(R.id.detailFilmFragment, bundle)
    }
}