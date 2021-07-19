package com.example.filmapp.framework.main.ui.home.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.framework.main.ui.home.HomeFragment
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.utils.RoundedTransformation
import com.example.filmapp.model.entites.Film
import com.squareup.picasso.Picasso

private const val CAPACITY = 50
private const val RADIUS = 25
private const val DEFAULT_MARGIN = 0
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

class SubFilmsAdapter(
    private var onItemViewClickListener: FilmFragment.OnItemViewClickListener?,
    private var onScrollToLastListener: HomeFragment.OnScrollToLastListener?
) :
    RecyclerView.Adapter<SubFilmsAdapter.FilmsViewHolder>() {


    private var films = ArrayList<Film>(CAPACITY)

    inner class FilmsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemFilmPreviewBinding.bind(item)
        fun bind(film: Film) = with(binding) {
            postersTitle.text = film.title
            imagePosters.setImageResource(R.drawable.kinkongposters)
            Picasso
                .get()
                .load("$IMAGE_URL${film.poster}")
                .fit()
                .transform(RoundedTransformation(RADIUS, DEFAULT_MARGIN))
                .into(imagePosters)
            score.text = film.voteAverage.toString()
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_film_preview,
            parent,
            false
        )
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        println(position)
        if (position == films.size / 2) {
            onScrollToLastListener?.onUpdate()
        }
        holder.bind(films[position])
    }

    override fun getItemCount() = films.size

    fun addFilms(films: ArrayList<Film>) {
        films.also { this.films = it }
    }

}