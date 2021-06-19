package com.example.filmapp.ui.main.Main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.model.entites.Film

class MainAdapter : RecyclerView.Adapter<MainAdapter.FilmViewHolder>() {
    val films = ArrayList<Film>(50)

    class FilmViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemFilmPreviewBinding.bind(item)
        fun bind(film: Film) = with(binding) {
            imagePosters.setImageResource(film.id)
            postersTitle.text = film.title
            score.text = film.voteAverage.toString()
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

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun addFilms(films: ArrayList<Film>) {
        this.films.addAll(films)
        notifyDataSetChanged()
    }
}