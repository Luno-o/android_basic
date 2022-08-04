package com.example.roomdao.data.db
//
import androidx.room.*
import com.example.roomdao.data.db.models.Customer
import com.example.roomdao.data.db.models.CustomerContract


@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(customers: List<Customer>)

    @Query("SELECT * FROM ${CustomerContract.TABLE_NAME}")
    suspend fun getAllUsers(): List<Customer>

    @Delete
    suspend fun removeCustomer(customer: Customer)

    @Query("DELETE FROM ${CustomerContract.TABLE_NAME} WHERE ${CustomerContract.Columns.ID} = :id")
    suspend fun removeCustomerById(id: Long)

    @Query("SELECT * FROM ${CustomerContract.TABLE_NAME} WHERE ${CustomerContract.Columns.ID} = :userId")
    suspend fun getCustomerById(userId: Long): Customer?

    @Update
    suspend fun updateCustomer(customer: Customer)



//    @Transaction
//    @Query("SELECT * FROM ${CustomerContract.TABLE_NAME} WHERE ${CustomerContract.Columns.ID} = :id")
//    suspend fun getCustomerWithAddress(id:Long): CustomerWithAddress?
//
//@Transaction
//@Query("SELECT * FROM ${CustomerContract.TABLE_NAME} WHERE ${CustomerContract.Columns.ID} = :id")
//    suspend fun getCustomersWithRelations(id: Long):CustomerWithOrders?
//    @Query(
//        "SELECT * FROM user" +
//                "JOIN book ON user.id = book.user_id"
//    )
//    fun loadUserAndBookNames(): Map<User, List<Book>>
}