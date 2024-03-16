package com.cs4520.assignment1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class DBProduct( @PrimaryKey(autoGenerate = false) val id: Int = 1,
                     val name: String?,
                    val expiryDate: String?,
                    val price: Int?,
                    val type: String?)