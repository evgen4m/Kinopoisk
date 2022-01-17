package com.esoft.kinopoisk.presentation.listFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    private var navController: NavController? = null

    private companion object {
        const val filmId = "filmId"
    }


    private val presenter by lazy {
        val repository = (activity?.application as KinopoiskApp).filmsRepository
        val getAllFilmsUseCase = GetAllFilmsUseCase(filmsRepository = repository!!)
        val getFilmGenresUseCase = GetFilmGenresUseCase(filmsRepository = repository!!)
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
        _binding = FragmentListFilmsBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val toolbar = binding.listFragmentToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
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

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getAllFilms(list: ArrayList<Film>) {
        adapterFilms.listFilms = list
    }

    override fun getGenres(set: Set<Genres>) {
        adapterFilms.setGenres = set as MutableSet<Genres>
    }

    override fun openDetailScreen(id: Int) {
        val bundle = Bundle()
        bundle.putInt(filmId, id)
        navController!!.navigate(R.id.detailFilmFragment, bundle)
    }
}