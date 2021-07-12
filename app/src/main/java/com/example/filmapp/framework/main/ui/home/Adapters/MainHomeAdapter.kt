package com.example.filmapp.framework.main.ui.home.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.databinding.HomeFragmentItemBinding
import com.example.filmapp.framework.main.ui.home.HomeFragment
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment

private const val defaultCapacity = 50

class MainHomeAdapter(
    private var onItemViewClickListener: FilmFragment.OnItemViewClickListener?,
    private var onScrollToLastListener: HomeFragment.OnScrollToLastListener?
) :
    RecyclerView.Adapter<MainHomeAdapter.HomePageViewHolder>() {

    private var items = ArrayList<Item>(defaultCapacity)
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class HomePageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding: HomeFragmentItemBinding = HomeFragmentItemBinding.bind(item)
        fun bind(item: Item) = with(binding) {
            titleCategory.text = item.category

            val context = subRcView.context
            subRcView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val adapter = SubFilmsAdapter(onItemViewClickListener, onScrollToLastListener)
            subRcView.adapter = adapter
            adapter.addFilms(item.films)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.home_fragment_item,
            parent,
            false
        )
        return HomePageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


    fun addItems(items: ArrayList<Item>) {
        items.also { this.items = it }
        notifyItemInserted(1)
    }
}