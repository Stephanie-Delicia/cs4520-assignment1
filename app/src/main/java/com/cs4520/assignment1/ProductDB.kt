package com.cs4520.assignment1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


// Class for product database
@Database(entities = [DBProduct::class], version = 3)
abstract class  ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        private var instance: ProductDB? = null

        @Synchronized
        fun getInstance(ctx: Context): ProductDB {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, ProductDB::class.java,
                    "product_table"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: ProductDB) {
            val productDao = db.productDao()
        }
    }
}

