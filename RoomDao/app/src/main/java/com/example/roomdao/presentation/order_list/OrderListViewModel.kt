package com.example.roomdao.presentation.order_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.roomdao.data.*
import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.*
import kotlinx.coroutines.launch


class OrderListViewModel(application: Application): AndroidViewModel(application){
private val productRepository = ProductRepository()
    private val customerRepository = CustomerRepository()
    private val addressRepository = AddressRepository()
    private val orderRepository = OrderRepository()
private val orderPricesRepository = OrderPricesRepository()


  private  val productMutableLiveData = MutableLiveData<List<ProductWithCount>>()
    val productLiveData: LiveData<List<ProductWithCount>>

    get() = productMutableLiveData


    fun loadList(){
        viewModelScope.launch {
            try {
                Database.instance.withTransaction {
                Log.d("viewmodel"," launch load list")
                val order = orderRepository.getActiveOrder()
                val orderPrices = orderPricesRepository.getOrderPrice(order.id)
                if (orderPrices.isEmpty()){
                    Log.d("viewmodel","order price is empty")
                    productMutableLiveData.postValue(emptyList())
                }else{
                    Log.d("viewmodel"," order price not empty")
                val listProductWithCount:MutableList<ProductWithCount> = mutableListOf()
                for (orderPrice in orderPrices){
                    val product  = productRepository.getProductById(orderPrice.productId)!!
                    listProductWithCount.add(ProductWithCount(product.title,product.price,
                        product.avatar,product.description,orderPrice.count))

                }
                    Log.d("viewmodel"," productlistWithCount  = $listProductWithCount")
            productMutableLiveData.postValue(listProductWithCount)
                }
                }
            }catch (t: Throwable){
                Log.d("viewmodel"," catch throwable  = $t")
                productMutableLiveData.postValue(emptyList())
            }
        }
    }

}