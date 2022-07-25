package com.example.filles.ui.main

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.filles.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
private val fragment = this
    private var downloadListAssets = emptyList<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isFirstStart()){
        try {
resources.assets.open("text.txt")
    .bufferedReader()
    .use {
       val text = it.readText()
        downloadListAssets = text.lines()
    }
            lifecycleScope.launch{
            downloadListAssets.map {
                viewModel.getFileWithDownloadManager(fragment,it)
            }
            }
        }catch (t :Throwable){

        }
        }
        button.setOnClickListener {
            lifecycleScope.launch {
viewModel.getFile(editTextTextPersonName.text.toString(),requireContext())
            }

        }
        button2.setOnClickListener {

            lifecycleScope.launch{
            viewModel.getFileWithDownloadManager(fragment,editTextTextPersonName.text.toString())
            }
        }
    }


}