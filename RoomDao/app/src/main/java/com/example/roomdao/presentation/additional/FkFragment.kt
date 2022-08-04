//package com.example.roomdao.presentation.additional
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import com.example.roomdao.data.db.Database
//import com.example.roomdao.data.db.models.Product
//import com.example.roomdao.data.db.models.OrderStatuses
//import kotlinx.coroutines.launch
//import timber.log.Timber
//
//
//class FkFragment : Fragment() {
//private val purchaseDao = Database.instance.orderDao()
//private val userDao = Database.instance.customerDao()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////savePurchases()
////    }
//
////    private fun savePurchases(){
////val products = listOf(
////    Product(id = 0, userId = 1, price = 100,OrderStatuses.CREATED),
////    Product(id = 0, userId = 100, price = 100,OrderStatuses.CREATED
////    )
////)
////        lifecycleScope.launch {
////            purchaseDao.insertOrder(products)
////        }
////    }
////    private fun testRelation(){
////        lifecycleScope.launch {
////           val usersWithRelations =  userDao.getAllCustomersWithRelations()
////            Timber.d(usersWithRelations.toString())
////        }
//   }
//}