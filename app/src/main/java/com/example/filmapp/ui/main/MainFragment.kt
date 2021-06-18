package com.example.filmapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.MainFragmentBinding
import com.example.filmapp.model.repository.RepositoryImpl
import com.example.filmapp.ui.main.adapter.MainAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var binding : MainFragmentBinding
    private lateinit var viewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel =  ViewModelProvider(this).get(MainViewModel :: class.java)
        initRcView()
        return binding.root
    }

    private fun initRcView() = with(binding){
        val repository = RepositoryImpl()
        rcView.layoutManager = GridLayoutManager(context,3)

        val adapter = MainAdapter()
        adapter.addFilms(repository.init().films)
        rcView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}