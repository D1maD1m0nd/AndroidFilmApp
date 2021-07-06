package com.example.filmapp.framework.main.ui.home.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.model.entites.Film
import com.squareup.picasso.Picasso

class SubFilmsAdapter(private var onItemViewClickListener: FilmFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<SubFilmsAdapter.FilmsViewHolder>() {
    inner class FilmsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val imageStorageUrl = "https://image.tmdb.org/t/p/w500/"
        private val binding = ItemFilmPreviewBinding.bind(item)
        fun bind(film: Film) = with(binding) {
            postersTitle.text = film.title
            imagePosters.setImageResource(R.drawable.kinkongposters)
            Picasso
                .get()
                .load("$imageStorageUrl${film.poster}")
                .into(imagePosters)
            score.text = film.voteAverage.toString()
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }

    private var films = ArrayList<Film>(50)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_film_preview,
            parent,
            false
        )
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount() = films.size

    fun addFilms(films: ArrayList<Film>) {
        films.also { this.films = it }
        notifyDataSetChanged()
    }

}