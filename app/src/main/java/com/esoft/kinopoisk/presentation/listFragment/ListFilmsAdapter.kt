package com.esoft.kinopoisk.presentation.listFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esoft.kinopoisk.R
import com.esoft.kinopoisk.databinding.ListFilmItemBinding
import com.esoft.kinopoisk.databinding.ListGenresItemBinding
import com.esoft.kinopoisk.databinding.TitleItemBinding
import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.Genres
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.Comparator


class ListFilmsAdapter(private val onItemClick: (Film) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var context: Context? = null
    private var lastSelectedPosition = RecyclerView.NO_POSITION

    private companion object {
        const val GENRES_TYPE = 0
        const val FILM_TYPE = 1
        const val GENRES_TITLE = 2
        const val FILM_TITLE = 3
    }

    private val filmListAll = ArrayList<Film>()

    var listFilms = ArrayList<Film>()
        set(value) {
            field = value
            sort(value)
            filmListAll.addAll(listFilms)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        var holder: RecyclerView.ViewHolder? = null
        when (viewType) {
            GENRES_TITLE -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.title_item, parent, false)
                holder = TitleGenresHolder(view = view)
            }
            GENRES_TYPE -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.list_genres_item, parent, false)
                holder = GenresViewHolder(view = view)
            }
            FILM_TYPE -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.list_film_item, parent, false)
                holder = FilmsViewHolder(onItemClick = onItemClick, view = view)
            }
            FILM_TITLE -> {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.title_item, parent, false)
                holder = TitleFilmHolder(view = view)
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder.itemViewType) {
            GENRES_TYPE -> {
                val viewHolder = holder as GenresViewHolder
                val genres = setGenres.elementAt(position - 1)
                viewHolder.bind(genres = genres)
                viewHolder.binding.textGenres.isSelected = lastSelectedPosition == position
            }
            FILM_TYPE -> {
                val viewHolder = holder as FilmsViewHolder
                viewHolder.bind(film = listFilms[position - 2 - setGenres.size])
                val url = listFilms[position - 2 - setGenres.size].image_url
                Glide
                    .with(context!!)
                    .load(url)
                    .placeholder(R.drawable.ic_not_found)
                    .into(holder.binding.imageFilm)

            }

            FILM_TITLE -> {
                val viewHolder = holder as TitleFilmHolder
                viewHolder.bind()
            }

            GENRES_TITLE -> {
                val viewHolder = holder as TitleGenresHolder
                viewHolder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        val genres = setGenres.size + 2
        return genres + listFilms.size
    }

    inner class FilmsViewHolder(private val onItemClick: (Film) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = ListFilmItemBinding.bind(view)
        fun bind(film: Film) {
            binding.apply {
                textFilmName.text = film.localized_name
            }

            itemView.setOnClickListener {
                onItemClick(film)
            }
        }
    }

    inner class GenresViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = ListGenresItemBinding.bind(view)
        fun bind(genres: Genres) {
            binding.apply {
                textGenres.text = genres.name
                textGenres.setOnClickListener {
                    textGenres.isSelected = !textGenres.isSelected
                    if (textGenres.isSelected) {
                        notifyItemChanged(lastSelectedPosition)
                        lastSelectedPosition = layoutPosition
                        notifyItemChanged(lastSelectedPosition)
                        filter.filter(genres.name)
                    }
                    else {
                        notifyItemChanged(lastSelectedPosition)
                        lastSelectedPosition = RecyclerView.NO_POSITION
                        notifyItemChanged(lastSelectedPosition)
                        listFilms.clear()
                        listFilms.addAll(filmListAll)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    inner class TitleGenresHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TitleItemBinding.bind(view)
        fun bind() {
            binding.titleGenres.setText(R.string.textGenres)
        }
    }

    inner class TitleFilmHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TitleItemBinding.bind(view)
        fun bind() {
            binding.titleGenres.setText(R.string.textFilm)
        }
    }

    override fun getFilter(): Filter {
        return filterList
    }

    private val filterList: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Film> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(filmListAll)
            } else {
                for (item in filmListAll) {
                    for (genres in item.genres!!) {
                        if (genres.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(item)
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listFilms.clear()
            listFilms.addAll(results.values as Collection<Film>)
            notifyDataSetChanged()
        }
    }

    private fun sort(list: ArrayList<Film>) {
        Collections.sort(list, object : Comparator<Film> {
            override fun compare(p0: Film, p1: Film): Int {
                return p0.localized_name!!.compareTo(p1.localized_name!!)
            }
        })
    }
}