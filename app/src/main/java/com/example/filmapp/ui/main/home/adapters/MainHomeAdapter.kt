package com.example.filmapp.ui.main.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.filmapp.R
import com.example.filmapp.databinding.HomeFragmentItemBinding


class MainHomeAdapter : RecyclerView.Adapter<MainHomeAdapter.HomePageViewHolder>() {
    private var items = ArrayList<Item>(50)

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
            val adapterChild = SubFilmsAdapter()
            subRcView.adapter = adapterChild
            //holder.rcView.setRecycledViewPool(viewPool)
            adapterChild.addFilms(item.films)
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
        notifyDataSetChanged()
    }
}