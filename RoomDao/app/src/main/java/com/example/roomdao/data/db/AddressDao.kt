package com.example.roomdao.data.db

import androidx.room.*
import com.example.roomdao.data.db.models.Address
import com.example.roomdao.data.db.models.AddressContract

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(listOfString: List<Address>)

    @Query("SELECT * FROM ${AddressContract.TABLE_NAME} WHERE ${AddressContract.Columns.ID} = :id")
    suspend fun getAddressById(id: Long):Address?

    @Query("DELETE FROM ${AddressContract.TABLE_NAME} WHERE ${AddressContract.Columns.ID} = :id")
    suspend fun deleteAddressById(id: Long)

    @Delete
    suspend fun removeAddress(address: Address)

    @Update
    suspend fun updateAddress(address: Address)
}