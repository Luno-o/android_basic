package com.example.flowexample.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowexample.databinding.FragmentSecondBinding
import com.example.networking.MovieAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {
    private var movieAdapter: MovieAdapter? = null
    private var _binding: FragmentSecondBinding? = null
    private val viewModel: SecondFragmentViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        viewModel.observeMovies()
        observeViewModel()
    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(binding.recyclerViewMovie) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                movieAdapter?.items = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieAdapter = null
    }
}