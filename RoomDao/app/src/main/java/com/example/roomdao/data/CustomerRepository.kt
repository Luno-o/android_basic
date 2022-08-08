package com.example.roomdao.data

import android.util.Patterns
import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.Customer

class CustomerRepository {
    private val customerDao = Database.instance.customerDao()
private val thisCustomer = Customer(1,1,"yui@maol.ti",
    "23239222","Yui","Hong",null
    ,22
)

    suspend fun getOwnerCustomer(): Customer{
       return getCustomerById(thisCustomer.id)!!
    }
    suspend fun saveCustomer(customer: Customer){
        if (isCustomerValid(customer).not()) throw IncorrectFormException()
        customerDao.insertUsers(listOf(customer))
    }
    suspend fun updateCustomer(customer: Customer){
        if (isCustomerValid(customer).not()) throw IncorrectFormException()
        customerDao.updateCustomer(customer)
    }
    suspend fun removeCustomer(userId: Long){
customerDao.removeCustomerById(userId)
    }
    suspend fun getCustomerById(userId: Long):Customer?{
        return customerDao.getCustomerById(userId)
    }

    suspend fun getAllCustomers():List<Customer>{
        return customerDao.getAllUsers()
    }
    private fun isCustomerValid(customer:Customer): Boolean{
        return customer.firstName.isNotBlank()&&
                customer.lastName.isNotBlank()&&
                Patterns.EMAIL_ADDRESS.matcher(customer.email).matches()
    }
}