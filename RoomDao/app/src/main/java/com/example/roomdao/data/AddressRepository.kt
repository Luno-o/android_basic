package com.example.roomdao.data


import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.Address
import com.example.roomdao.data.db.models.CustomerWithAddress

class AddressRepository {
    private val addressDao = Database.instance.addressDao()


    suspend fun saveAddress(address: Address){
        if (isAddressValid(address).not()) throw IncorrectFormException()
        addressDao.insertAddress(listOf(address))
    }
    suspend fun updateAddress(address: Address){
        if (isAddressValid(address).not()) throw IncorrectFormException()
        addressDao.updateAddress(address)
    }
    suspend fun removeAddress(address: Address){
        addressDao.removeAddress(address)
    }
    suspend fun getAddressById(addressId: Long): Address?{
        return addressDao.getAddressById(addressId)
    }

    private fun isAddressValid(address: Address): Boolean{
        return address.deliveryAddress.isNotBlank()
    }

     suspend fun getCustomerWithAddress(id:Long): CustomerWithAddress{
        return addressDao.getCustomerWithAddress(id)
    }
}