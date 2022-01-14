package com.esoft.kinopoisk.presentation.listFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.databinding.ListFilmItemBinding
import com.esoft.kinopoisk.databinding.ListGenresItemBinding
import com.esoft.kinopoisk.databinding.TitleItemBinding
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import kotlin.collections.ArrayList

class ListFilmsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    private companion object {
        const val GENRES_TYPE = 0
        const val FILM_TYPE = 1
        const val GENRES_TITLE = 2
        const val FILM_TITLE = 3
    }

    var listFilms = ArrayList<Film>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var setGenres = HashSet<Genres>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeaderGenres(position = position) -> GENRES_TITLE
            isGenres(position = position) -> GENRES_TYPE
            isHeaderFilm(position = position) -> FILM_TITLE
            else -> FILM_TYPE
        }
    }

    private fun isHeaderGenres(position: Int): Boolean {
        return position == 0
    }

    private fun isGenres(position: Int): Boolean {
        return position < setGenres.size + 1
    }

    private fun isHeaderFilm(position: Int): Boolean {
        return position == setGenres.size + 1
    }

    private fun isFilm(position: Int): Boolean {
        return position > setGenres.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        var holder: RecyclerView.ViewHolder? = null
        when(viewType) {
            GENRES_TITLE -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.title_item, parent, false)
                holder = TitleGenresHolder(view = view)
            }
            GENRES_TYPE -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.list_genres_item, parent, false)
                holder = GenresViewHolder(view = view)
            }
            FILM_TYPE -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.list_film_item, parent, false)
                holder = FilmsViewHolder(view = view)
            }
            FILM_TITLE -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.title_item, parent, false)
                holder = TitleFilmHolder(view = view)
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            GENRES_TYPE -> {
                val viewHolder = holder as GenresViewHolder
                viewHolder.bind(genres = setGenres.elementAt(position - 1))
            }
            FILM_TYPE -> {
                val viewHolder = holder as FilmsViewHolder
                viewHolder.bind(film = listFilms[position - 2 - setGenres.size])
                val url = listFilms[position - 2 - setGenres.size].image_url
                Glide
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.not_found)
                    .into(holder.binding.imageFilm)

            }

            FILM_TITLE -> {
                val viewHolder = holder as TitleFilmHolder
                viewHolder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        val genres = setGenres.size + 2
        return genres + listFilms.size
    }

    inner class FilmsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = ListFilmItemBinding.bind(view)
        fun bind(film: Film) {
            binding.apply {
                textFilmName.text = film.localized_name
            }

            /*itemView.setOnClickListener {
                onItemClick(film)
            }*/
        }
    }

    inner class GenresViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ListGenresItemBinding.bind(view)
        fun bind(genres: Genres) {
            binding.apply {
                textGenres.text = genres.genres
            }

            /*itemView.setOnClickListener {
                onItemClick(film)
            }*/
        }
    }

    inner class TitleGenresHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = TitleItemBinding.bind(view)
        fun bind() {
            binding.titleGenres.text = "Жанры"
        }
    }

    inner class TitleFilmHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = TitleItemBinding.bind(view)
        fun bind() {
            binding.titleGenres.text = "Фильмы"
        }
    }
}