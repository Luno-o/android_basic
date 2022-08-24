package com.example.flowexample.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowexample.R
import com.example.flowexample.models.MovieType
import com.example.flowexample.databinding.FragmentSearchMovieBinding
import com.example.flowexample.utils.checkedChangesFlow
import com.example.flowexample.utils.textChangeFlow
import com.example.networking.MovieAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.io.IOException
import kotlin.random.Random


class SearchMovieFragment : Fragment() {
    private var movieAdapter: MovieAdapter? = null
    private var _binding: FragmentSearchMovieBinding? = null
    private var currentJob: Job? = null
    private val viewModel: SearchMovieViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bind(flowSearchEditText(), flowRadioButton())
        initList()
        observeViewModel()
        binding.toSecondFragment.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
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
        viewModel.moviesLD.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
        }
        viewModel.progressLD.observe(viewLifecycleOwner){
            showProgress(it)
        }
    }

    private fun flowRadioButton(): Flow<MovieType> {
        return binding.radioGroup.checkedChangesFlow().onStart {
                Timber.d("Flow radio button start")
                binding.radioGroup.check(R.id.radio_movie)
                emit(R.id.radio_movie)
            }.map {

                when (it) {
                    R.id.radio_movie ->{
                    Timber.d("${MovieType.MOVIE}")
                    MovieType.MOVIE
                    }
                    R.id.radio_series -> {Timber.d("${MovieType.SERIES}")
                        MovieType.SERIES}
                    R.id.radio_episode -> {Timber.d("${MovieType.EPISODE}")
                        MovieType.EPISODE}
                    else -> error("not expected value")
                }
            }
                .catch {
                    Toast.makeText(context, "Need to chose type movie", Toast.LENGTH_SHORT).show()
                }


    }

    private fun flowSearchEditText(): Flow<String> {
        return binding.searchEditText.textChangeFlow().onStart {
                Timber.d("Edit text flow start")
                emit("")
            }.onEach { Timber.d(it) }

    }
//    private fun flowOperators() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            flow {
//                Timber.d("thread on flow = ${Thread.currentThread().name}")
//                delay(1000)
//                emit(1)
//                delay(1000)
//                emit(2)
//            }
//                .flowOn(Dispatchers.IO)
//                .onEach {
//                    Timber.d("Thread on each1 = ${Thread.currentThread().name}")
//                }
//                .flowOn(Dispatchers.Main)
//                .onEach {
//                    Timber.d("Thread on each2 = ${Thread.currentThread().name}")
//                }
//                .map { it * it }
//                .flowOn(Dispatchers.IO)
//                .onEach {
//                    Timber.d("Thread before collect = ${Thread.currentThread().name}")
//                }
//                .collect {
//                    Timber.d("consume value $it")
//                }
//
//            combine(
//                binding.checkBox.checkedChangesFlow().onStart { emit(false) },
//                binding.searchEditText.textChangeFlow().onStart { emit("") }
//            ) { onlyFemale, text ->
//                onlyFemale to text
//
//            }
//                .debounce(500)
//
//                .distinctUntilChanged()
//                .onEach {
//                    showProgress(true)
//                    Timber.d("start search $it")
//                }
//                .mapLatest { (onlyFemale, text) -> searchUsers(onlyFemale, text) }
//                .onEach {
//                    showProgress(false)
//                    Timber.d("end search $it")
//                }
//                .map { it.map { it.toString() }.joinToString("\n") }
//                .collect {
//                    binding.textviewFirst.text = it
//                }
//
//        }
//    }

    private fun showProgress(boolean: Boolean) {
        if (boolean) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    private suspend fun searchUsers(onlyFemale: Boolean, query: String): List<User> {
//        delay(1000)
//        return users.filter {
//            val isGenderCorrect = (!onlyFemale || it.gender == Gender.FEMALE)
//            it.name.contains(query, ignoreCase = true) && isGenderCorrect
//        }
//    }

//    private fun flowFromCallback() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            binding.flowEditText.textChangeFlow().collect {
//                Timber.d(it)
//            }
//        }
//    }

//    private fun flowGenerator() {
//        val generator = createFlowGenerator()
//        binding.buttonFirst.setOnClickListener {
//            currentJob?.cancel()
//            currentJob = viewLifecycleOwner.lifecycleScope.launch {
//                generator.collect {
//                    Timber.d("consume value $it")
//                    binding.textviewFirst.text = it.toString()
//                }
//            }
//        }
//    }

    private fun createFlowGenerator(): Flow<Int> {
        return flow {
            Timber.d("start flow")
            while (true) {
                val value = Random.nextInt()
                Timber.d("emit value $value")
                emit(value)
                delay(1000)
            }
        }
    }

    private fun errorHandling() {
        flow {

            delay(1000)
            throw IOException("network unavailable")
            emit(1)

        }
            // .catch {  Timber.e(it)}
            .catch { emit(-1) }
            //  .catch {  throw it}
            .map { it * 2 }
            .catch { Timber.e("map exception = $it") }
            .map { error("test exception") }
            .catch { Timber.e("map 2 exception = $it") }
            .onEach { Timber.d("element = $it") }
            .catch { Timber.e("before collect exception = $it") }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun flowBuilders() {
        flowOf(1, 2, 3)
        val flowFromSuspendLambda = suspend {
            delay(1000)
            10
        }.asFlow()
        (0..100).asFlow()
        arrayOf(123, 123).asFlow()
        listOf(123, 123).asFlow()
        setOf(123, 123).asFlow()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieAdapter = null
    }
}