package com.cs4520.assignment1

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.graphics.Color as graphicsColor
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import kotlin.math.roundToInt

class MainActivity : FragmentActivity() {
    private var productList: List<Product>? = ArrayList()
    private var database: ProductDB? = null
    private var isLoading: Boolean = false
    private var isTextVisible: Boolean = false
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenNavigation()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun ScreenNavigation() {
        // This function allows for navigation between the login screen and the product list screen
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable( route = Screen.ProductListScreen.route) {
                ProductListScreen()
            }
        }
    }

    @Composable
    fun LoginScreen(navController: NavController) {
        // Contains fields for username, password, and a login button
        Box(
            contentAlignment = Alignment.Center, // you apply alignment to all children
            modifier = Modifier.fillMaxSize()
        ) {ConstraintLayout {
            // Create references for the composable to constrain
            val (button, userField, passField, titleText) = createRefs()
            var userFieldText by remember { mutableStateOf("") }
            var passFieldText by remember { mutableStateOf("") }
            val title by remember { mutableStateOf("Assignment 5: Crafting with compose") }
            val startGuideline = createGuidelineFromStart(0.2f)
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
                onClick = {
                    if ((userFieldText.toString() == "admin") and
                        (passFieldText.toString() == "admin")) {
                       Toast.makeText(this@MainActivity, "Successful login.", Toast.LENGTH_SHORT).show()
                        Log.i("Successful login", "LoginFragment")
                        navController.navigate(Screen.ProductListScreen.route)
                        userFieldText = ""
                        passFieldText = ""
            } else {
                Toast.makeText(this@MainActivity, "Invalid username/password. Hint: admin", Toast.LENGTH_SHORT).show()
            }},
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
    private fun ProductImage(prod: Product) {
        Image(
            painter = painterResource(id = prod.imageForDisplay()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(84.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }

    @Composable
    fun ProductItem(data: Product, modifier: Modifier = Modifier) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            //elevation = 2.sp,
            colors = CardDefaults.cardColors(
                containerColor = Color(data.backgroundColor().toColorInt())
            ),
            // backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))

        ) {
            Row {
                ProductImage(data)
                if (data.type == ProductType.Equipment) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)) {
                        Text(text = data.name, style = typography.h6)
                        Text(text = "$" + data.price.toString(), style = typography.caption)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)) {
                        Text(text = data.name, style = typography.h6)
                        Text(text = "Expiry: " + data.expiryDate, style = typography.caption)
                        Text(text = "$" + data.price.toString(), style = typography.caption)
                    }
                }

            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun ProductListScreen() {
        var products: MutableState<List<Product>?> = remember { mutableStateOf(productList) }
        var initialLoading by remember {mutableStateOf(false)} //replace initial loading
        var isLoading : MutableState<Boolean> = remember { mutableStateOf(isLoading) }
        var isTextVisible : MutableState<Boolean> = remember { mutableStateOf(isTextVisible) }

        var value = remember {0}
        fun getNextInt(): Int = value++

        database = this.let {
            Room.databaseBuilder(
                it,
                ProductDB::class.java,
                "product_table"
            ).fallbackToDestructiveMigration().build()
        }

        if (!initialLoading) {
            Log.i("initialLoading", "line 299")
            retrieveProductData(products, isLoading, isTextVisible)
            initialLoading = true
        }

        Box(contentAlignment = Alignment.Center, // you apply alignment to all children
            modifier = Modifier.fillMaxSize(),
        ) {
        ConstraintLayout {
            val (refreshText, refreshButton, loadingCircle) = createRefs()
            val startGuideline = createGuidelineFromStart(0.2f)
            val endGuideline = createGuidelineFromEnd(0.2f)

            if (isTextVisible.value) {
                Text("Unable to fetch any products. Refresh?",
                    modifier =
                    Modifier.constrainAs(refreshText) {
                        top.linkTo(parent.top, margin = 400.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    },
                    fontSize = 20.sp)
            } else {
                Spacer(modifier = Modifier.height(1.dp))
            }

            if (isLoading.value) {
                //Spacer(modifier = Modifier.height(1.dp))
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    // fillMaxSize()
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
            }

            if (isTextVisible.value) {
                Button(
                    modifier = Modifier.constrainAs(refreshButton) {
                        top.linkTo(refreshText.bottom, margin = 32.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    },
                    onClick = {
                        isTextVisible.value = false
                        retrieveProductData(products, isLoading, isTextVisible)
                        Log.i("refreshButton data:", products.value.toString())
                        Log.i("refreshButton data length:", products.value?.size.toString())
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF4D8EFF)),
                ) {
                    Text("Refresh")
                }
            } else {
                Spacer(modifier = Modifier.height(1.dp))
            }

            if (isLoading.value) {
                Log.i("isLoading.value", "line 274")
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(loadingCircle) {
                        bottom.linkTo(refreshText.top, margin = 32.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    }
                    ,
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun retrieveProductData(productList : MutableState<List<Product>?>,
                            isLoadVisible : MutableState<Boolean>,
                            isRefreshTextVisible : MutableState<Boolean>) {
        isLoadVisible.value = true
        isLoading = true
        val service = RetrofitClient.retrofit.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            var data: List<ApiProduct>?
            var response: Response<List<ApiProduct>>? = null
            try {
                response = service.getAllData("3")
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

            isLoadVisible.value = false
            isLoading = false
            isRefreshTextVisible.value = productList.value.isNullOrEmpty()
            isTextVisible = isRefreshTextVisible.value
            Log.i("Failed line 359, isTextVisible:", isRefreshTextVisible.value.toString())
            Log.i("Failed line 360, productList.value.isNullOrEmpty():", productList.value.isNullOrEmpty().toString())
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

sealed class Screen(val route:String){
    data object LoginScreen : Screen(route = "login_screen")
    data object ProductListScreen : Screen(route = "productList_screen")
}