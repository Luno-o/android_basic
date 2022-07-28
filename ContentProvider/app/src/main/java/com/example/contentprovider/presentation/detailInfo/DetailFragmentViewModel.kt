package com.example.contentprovider.presentation.detailInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.contentprovider.data.ContactRepository

class DetailFragmentViewModel(application: Application): AndroidViewModel(application){
    private val repository = ContactRepository(application)



    fun deleteContact()
}