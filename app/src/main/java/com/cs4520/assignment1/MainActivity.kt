package com.cs4520.assignment1

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import kotlin.math.roundToInt

class MainActivity : FragmentActivity() {
    var productList: List<Product>? = ArrayList()
    private var database: ProductDB? = null
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        setContent {
            ProductListScreen()
            //WelcomeScreen()

            // ProductListScreen()
//            val navController = rememberNavController()
//            NavHost(navController, startDestination = "welcome") {
//
//            }
        }
    }

    @Composable
    fun WelcomeScreen() {
        Box(
            contentAlignment = Alignment.Center, // you apply alignment to all children
            modifier = Modifier.fillMaxSize()
        ) {ConstraintLayout {
            // Create references for the composable to constrain
            val (button, userField, passField, titleText) = createRefs()
            var userFieldText by remember { mutableStateOf("") }
            var passFieldText by remember { mutableStateOf("") }
            val title by remember { mutableStateOf("Assignment 5: Crafting with compose") }
            // Create guideline from the start of the parent at 10% the width of the Composable
            val startGuideline = createGuidelineFromStart(0.2f)
            // Create guideline from the end of the parent at 10% the width of the Composable
            val endGuideline = createGuidelineFromEnd(0.2f)
            val gradientColors = listOf(Color(0xFFD900FF), Color(0xFF4D8EFF))

            Text(title,
                fontSize = 20.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                })

            Button(
                onClick = { /* Do something */ },
                colors = ButtonDefaults.buttonColors(Color(0xFF4D8EFF)),
                // Assign reference "button" to the Button composable
                // and constrain it to the top of the ConstraintLayout
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(passField.bottom, margin = 16.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }
            ) {
                Text("Log In")
            }

            // Assign reference "text" to the Text composable
            // and constrain it to the bottom of the Button composable
            TextField(
                userFieldText,
                onValueChange = {
                    userFieldText = it
                },
                singleLine = true,
                modifier = Modifier.constrainAs(userField) {
                    top.linkTo(titleText.bottom, margin = 16.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }
            )

            TextField(
                passFieldText,
                onValueChange = {
                    passFieldText = it
                },
                singleLine = true,
                modifier = Modifier.constrainAs(passField) {
                    top.linkTo(userField.bottom, margin = 16.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }
            ) }
        }
    }

    @Composable
    fun ProductItem(data: Product, modifier: Modifier = Modifier) {
        Row(modifier.fillMaxWidth()) {
            Text(text = data.name)
            // … other composables required for displaying `data`
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    @Preview
    fun ProductListScreen() {
        var products: MutableState<List<Product>?> = remember { mutableStateOf(productList) }
        var initialLoading by remember {mutableStateOf(false)} //replace initial loading
        var value = remember {0}
        fun getNextInt(): Int = value++

        // var refreshButton = null
        database = this.let {
            Room.databaseBuilder(
                it,
                ProductDB::class.java,
                "product_table"
            ).fallbackToDestructiveMigration().build()
        }

        Button(
            onClick = {
                retrieveProductData(products)
                // noProductsText!!.visibility = View.INVISIBLE
                Log.i("refreshButton data:", products.value.toString())
                Log.i("refreshButton data length:", products.value?.size.toString())
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF4D8EFF)),
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
        ) {
            Text("Refresh")
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(
                count = products.value!!.size,
                key = {
                    getNextInt()
                },
                itemContent = { index ->
                    ProductItem(products.value!![index])
                }
            )
        }


        //CircularProgressIndicator(
        //    modifier = Modifier.width(64.dp),
        //    color = MaterialTheme.colorScheme.secondary,
        //    trackColor = MaterialTheme.colorScheme.surfaceVariant,
        //)

       if (!initialLoading) {
            retrieveProductData(products)
            initialLoading = true
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun retrieveProductData(productList : MutableState<List<Product>?>) {
        //progressBar.visibility = View.VISIBLE
        //progressBar.isIndeterminate = true
       // val adapter = this
        val service = RetrofitClient.retrofit.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            var data: List<ApiProduct>?
            var response: Response<List<ApiProduct>>? = null
            try {
                response = service.getAllData("1")
            }  catch(e: IOException) { // no internet connection
                Log.i("no internet connection", "")
                val dbProds = database?.productDao()?.getAllProducts()
                if (dbProds != null) {
                    productList.value = dbProds.map {
                        if (it.type == "Equipment") {
                            Product.EquipmentProduct(it.name!!, it.expiryDate, it.price!!,
                                ProductType.Equipment)
                        } else {
                            Product.FoodProduct(it.name!!, it.expiryDate, it.price!!,
                                ProductType.Equipment)
                        }}
                }
            }
            if (response != null) {
                // val dbProds = database.productDao()
                if (response.isSuccessful) {
                    data = response.body()
                    val convertedData = convertApiDataToProductList(data)
                    productList.value = convertedData
                    var counter = 0
                    if (!data.isNullOrEmpty()) { // data is NOT null or empty
                        val databaseDAO = database?.productDao()
                        val dbProds =
                            productList.value?.map {
                                counter += 1
                                DBProduct(
                                    counter.toString(),
                                    it.name, it.expiryDate, it.price, it.type.toString())
                            }
                        if (dbProds != null) {
                            if (databaseDAO != null) {
                                databaseDAO.deleteAllProducts()
                                dbProds.map { databaseDAO.insert(it) }
                                Log.i("Database updated", databaseDAO.getAllProducts().toString())
                                Log.i("Sample", databaseDAO.getAllProducts()[0].name.toString())
                            }
                        }
                    }
                } else { // failed api call
                    Log.i("Failed API Call:", "")
                }
            }

            //progressBar.visibility = View.INVISIBLE
            //progressBar.isIndeterminate = false
//            withContext(Dispatchers.Main) {
//                adapter.notifyDataSetChanged()
//                if (!productList.isNullOrEmpty()) {
//                    productBinding.noProductsTextView.visibility = View.INVISIBLE
//                    productBinding.refreshButton.visibility = View.INVISIBLE
//                } else {
//                    productBinding.noProductsTextView.visibility = View.VISIBLE
//                    productBinding.refreshButton.visibility = View.VISIBLE
//                }
//            }
        }
    }

    private fun convertApiDataToProductList(list : List<ApiProduct>?) : List<Product>? {
        // first check that data is not null
        var filteredList: List<ApiProduct>?
        var returnList: List<Product>? = null
        if (list != null) {
            filteredList = list.filter {
                ((it.getExpiryDate() == null) &&
                        (it.getType() == "Equipment") &&
                        (it.getPrice() != null) &&
                        (it.getName() != null)) ||
                        ((it.getExpiryDate() != null) &&
                                (it.getType() == "Food") &&
                                (it.getPrice() != null) &&
                                (it.getName() != null))
            }

            returnList = filteredList.map {
                if (it.getType() == "Food") {
                    Product.FoodProduct(
                        it.getName()!!,
                        it.getExpiryDate(),
                        it.getPrice()!!.roundToInt(),
                        ProductType.Food
                    )
                } else {
                    Product.EquipmentProduct(
                        it.getName()!!,
                        it.getExpiryDate(),
                        it.getPrice()!!.roundToInt(),
                        ProductType.Equipment
                    )
                }
            }
        }
        return returnList
    }
}