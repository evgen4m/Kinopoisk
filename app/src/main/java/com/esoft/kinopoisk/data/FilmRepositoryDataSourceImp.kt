package com.esoft.kinopoisk.data

import com.esoft.kinopoisk.domain.model.Film
import com.esoft.kinopoisk.domain.model.FilmModel
import com.esoft.kinopoisk.domain.model.Genres
import com.esoft.kinopoisk.domain.model.Results
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmRepositoryDataSourceImp(private val api: FilmsApi): FilmRepositoryDataSource {

    override fun getAllFilms(callback: (List<Film>) -> Unit) {
        val films = ArrayList<Film>()
        val call = api.getAllFilms()
        call!!.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                val results: Results? = response.body()
                if (response.isSuccessful) {
                    if (results != null) {
                        for (film in results.list) {
                            films.add(
                                Film(
                                    id = film.id,
                                    localized_name = film.localized_name,
                                    name = film.name,
                                    year = film.year,
                                    rating = film.rating,
                                    image_url = film.image_url,
                                    description = film.description,
                                    genres = film.genres
                                )
                            )
                        }
                        callback(films)
                    }
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun getFilmGenres(callback: (Set<Genres>) -> Unit) {
        val genresSet = HashSet<Genres>()
        val call = api.getAllFilms()
        call!!.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                val results: Results? = response.body()
                if (response.isSuccessful) {
                    if (results != null) {
                        for (film in results.list) {
                            for (genres in film.genres) {
                               val newGenres = Genres (name = genres)
                                genresSet.add(newGenres)
                            }
                        }
                        callback(genresSet)
                    }
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun getFilmById(id: Int, callback: (Film) -> Unit) {
        api.getAllFilms()!!.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                val results: Results? = response.body()
                if (response.isSuccessful) {
                    if (results != null) {
                        for (film in results.list) {
                            if (id == film.id) {
                                val film = Film(
                                    id = film.id,
                                    localized_name = film.localized_name,
                                    name = film.name,
                                    year = film.year,
                                    rating = film.rating,
                                    image_url = film.image_url,
                                    description = film.description,
                                    genres = film.genres
                                )
                                callback(film)
                            }
                        }

                    }
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


}