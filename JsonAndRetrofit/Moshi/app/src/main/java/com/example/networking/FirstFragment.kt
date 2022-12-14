package com.example.networking

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networking.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModels()
    private var adapterMovie: MovieAdapter? = null
    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        adapterMovie?.notifyDataSetChanged()
        observeViewModel()

        binding.fab.setOnClickListener {
addRating()
        }
        binding.buttonSearch.setOnClickListener {
            if (binding.movieYearEditText.text.toString() == "") {
                movieViewModel.searchMovie(
                    binding.movieTittleEditText.text.toString()
                )
            } else if (binding.movieYearEditText.text.toString().toInt() > 2022
                || binding.movieYearEditText.text.toString().toInt() < 1900
            ) {
                Toast.makeText(context, "Enter correct year", Toast.LENGTH_SHORT).show()
            } else {
                movieViewModel.searchMovie(
                    binding.movieTittleEditText.text.toString()
                )
            }
        }

        val adapterExpose = ArrayAdapter(
            requireContext(),
            R.layout.list_item, resources.getTextArray(R.array.menu_dropdown)
        )
        (binding.menu.editText as AutoCompleteTextView).setAdapter(adapterExpose)
    }

    private fun initList() {
        adapterMovie = MovieAdapter()
        with(recyclerViewMovie) {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        movieViewModel.movies.observe(
            viewLifecycleOwner
        ) {
                adapterMovie?.items = it
        }
        movieViewModel.isLoadingData.observe(viewLifecycleOwner) {
            Log.d("Data", "isLoading = $it")
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.movieTittleEditText.isEnabled = false
                binding.movieYearEditText.isEnabled = false
                binding.buttonSearch.isEnabled = false
                binding.menu.isEnabled = false
                binding.recyclerViewMovie.isEnabled = false
            } else {
                Log.d("Data", "remove = progress Bar")
                binding.progressBar.visibility = View.GONE
                binding.movieTittleEditText.isEnabled = true
                binding.movieYearEditText.isEnabled = true
                binding.buttonSearch.isEnabled = true
                binding.menu.isEnabled = true
                binding.recyclerViewMovie.isEnabled = true
                binding.errorTextView.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }

        }
        movieViewModel.error.observe(viewLifecycleOwner) {
            binding.errorTextView.text = it.message
            binding.errorTextView.visibility = View.VISIBLE
            binding.retryButton.visibility = View.VISIBLE
            binding.retryButton.setOnClickListener {
                movieViewModel.searchMovie(
                    binding.movieTittleEditText.text.toString()
                )
                binding.errorTextView.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }
        }
        movieViewModel.hasMovieData.observe(viewLifecycleOwner){
            Log.d("Server", "hasMovie observer = $it")
            binding.errorTextView.text = "No Movie for this title"
            binding.errorTextView.visibility = View.VISIBLE
            binding.retryButton.visibility = View.VISIBLE
            binding.retryButton.setOnClickListener {
                movieViewModel.searchMovie(
                    binding.movieTittleEditText.text.toString()
                )
                binding.errorTextView.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }
        }
    }
    private fun addRating(){
movieViewModel.addRating()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapterMovie = null
    }
}