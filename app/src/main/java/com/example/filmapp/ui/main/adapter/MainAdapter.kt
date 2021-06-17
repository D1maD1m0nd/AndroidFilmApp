package com.example.filmapp.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.databinding.ItemFilmPreviewBinding
import com.example.filmapp.model.entites.Film

class MainAdapter : RecyclerView.Adapter<MainAdapter.FilmViewHolder>() {
    class FilmViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = ItemFilmPreviewBinding.bind(item)
        fun bind(film : Film) = with(binding) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}