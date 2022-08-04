package com.example.roomdao.data


import com.example.roomdao.data.db.Database
import com.example.roomdao.data.db.models.Product

class ProductRepository {
    private val productDao = Database.instance.productDao()


    suspend fun getAllProducts():List<Product>{
        return productDao.getAllProducts()
    }
    suspend fun removeProduct(product: Product){
        productDao.removeProduct(product)
    }
    suspend fun saveProduct(product: Product){
        if (isProductValid(product).not()) throw IncorrectFormException()
        productDao.addProduct(product)
    }


    private fun isProductValid(product: Product): Boolean{
        return product.title.isNotBlank()&&
                product.price != 0 &&
                product.description.isNotBlank()
    }
}