package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.multithreading.R
import kotlinx.android.synthetic.main.fragment_threading.*
import java.util.concurrent.Executors

class ThreadingFragment : Fragment(R.layout.fragment_threading) {
    private var movieAdapter: MovieAdapter? = null
    private val movieViewModel: MovieListViewModel by viewModels()
   private val mainHandler = Handler()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        movieAdapter?.notifyDataSetChanged()
        observeViewModel()

movieViewModel.requestMovie()

    }
    private fun initList() {
        movieAdapter = MovieAdapter()
        with(movieRecyclerView){
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
private fun observeViewModel(){
    movieViewModel.movies.observe(viewLifecycleOwner){
        mainHandler.post{
movieAdapter?.items = it
        }
        mainHandler.postDelayed({Toast.makeText(requireContext(),"Все фильмы добавлены",Toast.LENGTH_SHORT).show()},1000)
    }

}

}