package com.example.contentprovider.presentation.detailInfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.Contact
import com.example.contentprovider.data.ContactRepository
import com.example.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DetailFragmentViewModel(application: Application): AndroidViewModel(application){
    private val repository = ContactRepository(application)
private val deleteSuccessLiveEvent = SingleLiveEvent<Boolean>()
    val deleteSuccessLiveData: LiveData<Boolean>
    get() = deleteSuccessLiveEvent


   fun deleteContact(contact: Contact){
        viewModelScope.launch {
            try {
        repository.deleteContact(contact)
                Log.d("ViewModel","delete contact ${contact.name}")
                deleteSuccessLiveEvent.postValue(true)
            }catch (t:Throwable){
                Log.e("ViewModel","delete error",t)
            }
        }
    }
}