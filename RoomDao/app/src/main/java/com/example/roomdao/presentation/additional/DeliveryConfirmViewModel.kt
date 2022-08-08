package com.example.roomdao.presentation.additional

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.roomdao.data.AddressRepository
import com.example.roomdao.data.CustomerRepository

import com.example.roomdao.data.OrderRepository
import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.CustomerWithAddress
import com.example.roomdao.data.db.models.OrderStatuses
import kotlinx.coroutines.launch

class DeliveryConfirmViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrderRepository()
    private val addressRepository = AddressRepository()
    private val customerRepository = CustomerRepository()
    private val customerMutableLiveData = MutableLiveData<CustomerWithAddress>()
    val customerWithAddressLiveData: LiveData<CustomerWithAddress>
    get() = customerMutableLiveData
 fun confirmOrder(){
        viewModelScope.launch {
            Database.instance.withTransaction {
            val order = orderRepository.getActiveOrder()
            order.status = OrderStatuses.FINISHED
            orderRepository.updateOrder(order)
            }
        }
    }

    fun getCustomerWithAddress(){

        viewModelScope.launch {
        val customerWithAddress = addressRepository
            .getCustomerWithAddress(customerRepository.getOwnerCustomer().id)
            customerMutableLiveData.postValue(customerWithAddress)
        }

    }
}