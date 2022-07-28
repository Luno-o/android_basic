package com.example.contentprovider.presentation.add

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.ContactRepository
import com.example.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ContactAddViewModel(application: Application): AndroidViewModel(application) {
    private val contactRepository = ContactRepository(application)
    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val saveErrorLiveEvent = SingleLiveEvent<Unit>()

    val saveSuccessLiveData: LiveData<Unit>
    get() = saveSuccessLiveEvent

    val saveErrorLiveData: LiveData<Unit>
    get() = saveErrorLiveEvent

    fun save(name:String, phone: String, email: String = ""){
        viewModelScope.launch {
            try {
                contactRepository.saveContact(name,phone,email)
                saveSuccessLiveEvent.postValue(Unit)
            }catch (t: Throwable){
                Log.e("ContactAddViewModel","contact add error",t)
            }
        }
    }

}