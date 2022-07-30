package com.example.contentprovider.presentation.main


import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.FileProvider

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.BuildConfig
import com.example.contentprovider.R
import com.example.filles.ui.main.MainViewModel

import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private var downloadListAssets = emptyList<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        lifecycleScope.launch(Dispatchers.IO){
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
                viewModel.getFileWithDownloadManager(it)
            }
            }
        }catch (t :Throwable){

        }
        }
        }
        button.setOnClickListener {
            lifecycleScope.launch {
viewModel.getFile(editTextTextPersonName.text.toString(),requireContext())
            }

        }
        button2.setOnClickListener {

            lifecycleScope.launch{
            viewModel.getFileWithDownloadManager(editTextTextPersonName.text.toString())
            }
        }
        shareButton.setOnClickListener {
shareFile()
        }
    }
    private fun shareFile(){
        lifecycleScope.launch(Dispatchers.IO){
            if (Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED) return@launch
            val folder = context!!.getExternalFilesDir("external_storage/files/")
            val fileName = viewModel.getFileForShare()
            val file = File(folder,fileName)
            if (file.exists().not())return@launch
       val uri = FileProvider.getUriForFile(requireContext(),
            "${BuildConfig.APPLICATION_ID}.file_provider",
        file)
            Log.d("mainfragment","$uri")
            val intent = Intent(Intent.ACTION_SEND).apply {
putExtra(Intent.EXTRA_STREAM,uri)
                type = requireContext().contentResolver.getType(uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            val shareIntent = Intent.createChooser(intent,null)
            startActivity(shareIntent)
        }
    }
private fun isDownloading(isDownloading: Boolean){
    Log.d("mainfragment","$isDownloading")
    if (isDownloading){
        lifecycleScope.launch{

        progressBar.visibility = View.VISIBLE
        editTextTextPersonName.isEnabled = false
        button2.isEnabled = false
        }
    }else{
    lifecycleScope.launch{
        delay(500)
        progressBar.visibility = View.GONE
        editTextTextPersonName.isEnabled = true
        button2.isEnabled = true
    }
    }
}
    private fun observeViewModel(){
        viewModel.isDownloading.observe(viewLifecycleOwner){
            isDownloading(it)
        }
    }

}