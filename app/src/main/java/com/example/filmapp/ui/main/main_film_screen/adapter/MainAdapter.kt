package com.example.filmapp.ui.main.main_film_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.model.entites.Film
import com.example.filmapp.ui.main.main_film_screen.MainFragment

class MainAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainAdapter.FilmViewHolder>() {
    private var films = ArrayList<Film>(40)

    inner class FilmViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemFilmPreviewBinding.bind(item)
        fun bind(film: Film) = with(binding) {
            imagePosters.setImageResource(film.id)
            postersTitle.text = film.title
            score.text = film.voteAverage.toString()
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

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun addFilms(films: ArrayList<Film>) {
        this.films = films
        notifyDataSetChanged()
    }
}