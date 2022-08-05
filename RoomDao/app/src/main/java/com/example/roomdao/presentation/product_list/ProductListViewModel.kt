package com.example.roomdao.presentation.product_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdao.data.*
import com.example.roomdao.data.db.models.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductListViewModel(application: Application): AndroidViewModel(application){
private val productRepository = ProductRepository()
    private val customerRepository = CustomerRepository()
    private val addressRepository = AddressRepository()
    private val orderRepository = OrderRepository()
private val orderPricesRepository = OrderPricesRepository()


  private  val productMutableLiveData = MutableLiveData<List<Product>>()
    val productLiveData: LiveData<List<Product>>
    get() = productMutableLiveData
fun addOrder(order: Order){
    viewModelScope.launch {
    orderRepository.saveOrder(order)
    }
}
    fun addOrderPrices(orderPrices: OrderPrices){
        viewModelScope.launch {
        orderPricesRepository.saveOrder(orderPrices)
        }
    }
    fun addProductToOrder(product: Product){
        viewModelScope.launch {
val order = orderRepository.getActiveOrder()
            val orderWithProducts = orderRepository.getOrderWithProducts(order.id)
            val orderPricess = orderPricesRepository.getOrderPrice(order.id)
            if (orderPricess.isEmpty()){
                Log.d("viewmodelProduct"," order with products empty")
                orderPricesRepository.saveOrder(OrderPrices(
                 order.id, product.id,1
                ))
            }else{
                Log.d("viewmodelProduct"," not empty")

                val orderPrice = orderPricess.find { orderPrices ->
                    orderPrices.productId == product.id
                }!!
                val count = orderPrice.count
                orderPricesRepository.updateOrderPrice(OrderPrices(
                    order.id, product.id,count+1
                ))
            }
        }
    }
    fun loadList(){
        viewModelScope.launch {
            try {
            productMutableLiveData.postValue(productRepository.getAllProducts())
            }catch (t: Throwable){
                Timber.d(t)
                productMutableLiveData.postValue(emptyList())
            }
        }
    }
    fun addProduct(product: Product){
        viewModelScope.launch {
                productRepository.saveProduct(product)
            loadList()
        }
    }

    fun addAddress(address: Address){
        viewModelScope.launch {
            addressRepository.saveAddress(address)
        }
    }
    fun addCustomer(customer: Customer){
viewModelScope.launch {
    customerRepository.saveCustomer(customer)
}
    }
}