package com.example.viewmodelandnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singleliveeventsample.SingleLiveEvent


class TransportListViewModel: ViewModel() {
    private val repository = TransportRepository()
    private val transportLiveData = MutableLiveData(repository.getEmptyTransportList())
    val transports: LiveData<List<Transport>>
    get() = transportLiveData
private val showToastLiveData = SingleLiveEvent<Unit>()
val showToast:SingleLiveEvent<Unit>
get() = showToastLiveData

    fun addTransport(transport: Transport){
        val updatedList= listOf(transport) + transportLiveData.value.orEmpty()
        transportLiveData.postValue(updatedList)
    }

    fun deleteTransport(position: Int){
transportLiveData.postValue(repository.deleteTransport(transportLiveData.value.orEmpty(),position))
        showToastLiveData.postValue(Unit)
    }
}