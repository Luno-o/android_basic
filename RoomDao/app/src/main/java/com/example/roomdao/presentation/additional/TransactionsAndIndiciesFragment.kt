//package com.example.roomdao.presentation.additional
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.room.withTransaction
//import com.example.roomdao.data.db.Database
//import com.example.roomdao.data.db.models.Product
//import com.example.roomdao.data.db.models.OrderStatuses
//import com.example.roomdao.data.db.models.Customer
//import kotlinx.coroutines.launch
//import timber.log.Timber
//
//
//class TransactionsAndIndiciesFragment : Fragment() {
//    private val purchaseDao = Database.instance.orderDao()
//    private val userDao = Database.instance.customerDao()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
////    private fun atomicTransaction() {
////        lifecycleScope.launch {
////            Database.instance.withTransaction {
////                val customer = Customer(
////                    id = 1,
////                    firstName = "name",
////                    lastName = "last name",
////                    email = "email",
////                    avatar = null,
////                    age = 20
////                )
////                userDao.insertUsers(listOf(customer))
////
////                val product = Product(
////                    id = 1,
////                    userId = 1,
////                    price = 100,
////                    status = OrderStatuses.CREATED
////                )
////                purchaseDao.insertOrder(listOf(product))
////            }
////        }
////    }
//////    private fun insertNotTransactional(){
////        lifecycleScope.launch{
////            val startTime = System.currentTimeMillis()
////            (0..1000).forEach {
////                userDao.insertUsers(
////                    listOf(
////                    Customer(
////                        id = it.toLong(),
////                        firstName = "name$it",
////                        lastName = "lastname$it",
////                        email = "email$it",
////                        avatar = null,
////                        age = 25
////                    ))
////                )
////            }
////            Timber.d("insert time - ${System.currentTimeMillis() - startTime}")
////        }
////    }
////    private fun insertTransactional(){
////        lifecycleScope.launch{
////            val startTime = System.currentTimeMillis()
////            Database.instance.withTransaction {
////            (0..1000).forEach {
////                userDao.insertUsers(
////                    listOf(
////                        Customer(
////                            id = it.toLong(),
////                            firstName = "name$it",
////                            lastName = "lastname$it",
////                            email = "email$it",
////                            avatar = null,
////                            age = 30
////                        ))
////                )
////            }
////            }
////            Timber.d("insert time - ${System.currentTimeMillis() - startTime}")
////        }
////    }
//}