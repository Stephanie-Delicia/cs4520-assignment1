package com.cs4520.assignment1


import androidx.room.*
@Dao
interface ProductDAO {
    @Insert
    fun insert(prod: DBProduct)

    @Update
    fun update(prod: DBProduct)

    @Delete
    fun delete(prod: DBProduct)

    @Query("delete from product_table")
    fun deleteAllProducts()

    @Query("select * from product_table order by id desc")
    fun getAllProducts(): List<DBProduct>
}