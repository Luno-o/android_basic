package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.network.RemoteRepository
import kotlinx.android.synthetic.main.fragment_list_repository.*

class RepositoryListFragment: Fragment(R.layout.fragment_list_repository) {
private var repoAdapter: RepositoryAdapter? = null
private val repoViewModel: RepositoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
observeLiveData()
        repoViewModel.searchRepo()
    }
   private fun initList(){
        repoAdapter = RepositoryAdapter{
            val action = repoViewModel.repos.value?.get(it)?.let { repo->
                RepositoryListFragmentDirections.actionRepositoryListFragmentToDetailFragment(
                    repo
                )
            }
            if (action!=null){
                findNavController().navigate(action)
            }
        }
with(recyclerViewRepos){
    adapter = repoAdapter
    layoutManager = LinearLayoutManager(requireContext())
    setHasFixedSize(true)
}
    }
    private fun observeLiveData(){
repoViewModel.repos.observe(viewLifecycleOwner){
    repoAdapter?.items = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repoAdapter = null
    }
}