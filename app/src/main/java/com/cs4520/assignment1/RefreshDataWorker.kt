//package com.cs4520.assignment1
//
//import android.content.Context
//import android.util.Log
//import androidx.room.Room
//import androidx.work.CoroutineWorker
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import retrofit2.Response
//import java.io.IOException
//import kotlin.math.roundToInt
//
//class RefreshDataWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
//    private var database : ProductDB? = null
//    private var context = context
////    private fun setDatabase() {
////        database = this.let {
////            Room.databaseBuilder(
////                context,
////                ProductDB::class.java,
////                "product_table"
////            ).fallbackToDestructiveMigration().build()
////        }
////    }
//    override suspend fun doWork(): Result {
//        // setDatabase()
//        fetchData() // update product database
//        return Result.success()
//    }
//    private fun fetchData() {
//        // Fetches new data from API and updates the database
//        val service = RetrofitClient.retrofit.create(ApiService::class.java)
//        CoroutineScope(Dispatchers.IO).launch {
//            var data: List<ApiProduct>?
//            var response: Response<List<ApiProduct>>? = null
//            try {
//                response = service.getAllData("3")
//            }  catch(e: IOException) { // no internet connection
//                // no update needed
//            }
//            if (response != null) {
//                if (response.isSuccessful) {
//                    data = response.body()
//                    val convertedData = convertApiDataToProductList(data)
//                    var counter = 0
//                    if (!data.isNullOrEmpty()) { // data is NOT null or empty
//                        val databaseDAO = database?.productDao()
//                        val dbProds =
//                            convertedData?.map {
//                                counter += 1
//                                DBProduct(
//                                    counter.toString(),
//                                    it.name, it.expiryDate, it.price, it.type.toString())
//                            }
////                        if (dbProds != null) {
////                            if (databaseDAO != null) {
////                                // databaseDAO.deleteAllProducts()
////                               // dbProds.map { databaseDAO.insert(it) }
////                               // Log.i("Database updated by worker", databaseDAO.getAllProducts().toString())
////                               // Log.i("Sample", databaseDAO.getAllProducts()[0].name.toString())
////                            }
////                        }
//                    }
//                } else { // failed api call
//                    Log.i("Failed API Call:", "")
//                }
//            }
//            Log.i("Worker executed", "")
//        }
//    }
//
//    private fun convertApiDataToProductList(list : List<ApiProduct>?) : List<Product>? {
//        // first check that data is not null
//        var filteredList: List<ApiProduct>?
//        var returnList: List<Product>? = null
//        if (list != null) {
//            filteredList = list.filter {
//                ((it.getExpiryDate() == null) &&
//                        (it.getType() == "Equipment") &&
//                        (it.getPrice() != null) &&
//                        (it.getName() != null)) ||
//                        ((it.getExpiryDate() != null) &&
//                                (it.getType() == "Food") &&
//                                (it.getPrice() != null) &&
//                                (it.getName() != null))
//            }
//
//            returnList = filteredList.map {
//                if (it.getType() == "Food") {
//                    Product.FoodProduct(
//                        it.getName()!!,
//                        it.getExpiryDate(),
//                        it.getPrice()!!.roundToInt(),
//                        ProductType.Food
//                    )
//                } else {
//                    Product.EquipmentProduct(
//                        it.getName()!!,
//                        it.getExpiryDate(),
//                        it.getPrice()!!.roundToInt(),
//                        ProductType.Equipment
//                    )
//                }
//            }
//        }
//        return returnList
//    }
//}