package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skillbox.github.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()
    private val repoViewModel: RepositoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkStarred()
        observeViewModel()
        Glide.with(this)
            .load(args.repository.owner.avatarUrl)
            .into(avatarImageViewRepo)
        imageButtonOn.setOnClickListener {
            repoViewModel.unStar(args.repository.owner.login, args.repository.name)
        }
        imageButtonOff.setOnClickListener {
            repoViewModel.setStar(args.repository.owner.login, args.repository.name)
        }
        detailTextView.text = resources
            .getString(R.string.repo_info, args.repository.id, args.repository.name)
    }

    private fun checkStarred() {
        repoViewModel.checkStarred(args.repository.owner.login, args.repository.name)
    }

    private fun observeViewModel() {
        repoViewModel.star.observe(viewLifecycleOwner) {
            if (it) {
                imageButtonOn.visibility = View.VISIBLE
                imageButtonOff.visibility = View.GONE
                Log.d("starred", " star")
            } else {

                imageButtonOn.visibility = View.GONE
                imageButtonOff.visibility = View.VISIBLE
            }
        }
    }

}