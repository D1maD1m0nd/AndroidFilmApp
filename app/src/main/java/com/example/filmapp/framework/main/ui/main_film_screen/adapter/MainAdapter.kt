package com.example.filmapp.framework.main.ui.main_film_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.model.entites.Film
import com.squareup.picasso.Picasso

class MainAdapter(private var onItemViewClickListener: FilmFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainAdapter.FilmViewHolder>() {
    private var films = ArrayList<Film>(40)

    inner class FilmViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemFilmPreviewBinding.bind(item)
        private val imageStorageUrl = "https://image.tmdb.org/t/p/w500/"
        fun bind(film: Film) = with(binding) {
            imagePosters.setImageResource(R.drawable.kinkongposters)
            postersTitle.text = film.title
            score.text = film.voteAverage.toString()
            Picasso
                .get()
                .load("$imageStorageUrl${film.poster}")
                .fit()
                .into(imagePosters)
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_film_preview,
            parent,
            false
        )
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) =
        holder.bind(films[position])


    override fun getItemCount() = films.size

    fun addFilms(films: ArrayList<Film>) {
        films.also { this.films = it }
        notifyDataSetChanged()
    }
}