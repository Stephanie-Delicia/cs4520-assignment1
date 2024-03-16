package com.cs4520.assignment1

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresExtension
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.databinding.FragmentProductListBinding
import com.cs4520.assignment1.fragments.ProductListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.util.UUID
import kotlin.math.roundToInt


class RecyclerAdapter(
    private var progressBar: ProgressBar,
    private var productBinding: FragmentProductListBinding,
    private var productListFragment: ProductListFragment
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var productList: List<Product>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        if (productList != null) {
            holder.itemTitle.text = productList!![position].name
            val expiry = productList!![position].expiryDate
            if (expiry.isNullOrEmpty()) { // if no expiration date is given, make component invisible
                holder.itemPrice.isInvisible = true
                val price = holder.itemView.context.getString(R.string.dollar) +
                        productList!![position].price.toString()
                holder.itemExpiration.text = price
            } else {
                holder.itemPrice.isInvisible = false
                holder.itemExpiration.text = productList!![position].expiryDate
                val price = holder.itemView.context.getString(R.string.dollar) +
                        productList!![position].price.toString()
                holder.itemPrice.text = price
            }
            holder.itemImage.setImageResource(productList!![position].imageForDisplay())
            holder.itemBackground.setCardBackgroundColor(Color.parseColor(productList!![position].backgroundColor()))
        }

    }

    override fun getItemCount(): Int {
        return productList!!.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemExpiration: TextView
        var itemPrice: TextView
        var itemBackground: CardView
        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemExpiration = itemView.findViewById(R.id.item_expiration)
            itemPrice = itemView.findViewById(R.id.item_price)
            itemBackground = itemView.findViewById(R.id.card_view)
        }
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun retrieveProductData() {
        progressBar.visibility = View.VISIBLE
        progressBar.isIndeterminate = true
        val adapter = this
        val service = RetrofitClient.retrofit.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            var data: List<ApiProduct>?
            var response: Response<List<ApiProduct>>? = null
            try {
                response = service.getAllData("1")
            }  catch(e: IOException) { // no internet connection
                val dbProds = productListFragment.getDatabase()?.productDao()?.getAllProducts()
                if (dbProds != null) {
                    productList = dbProds.map {
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
                if (response.isSuccessful) {
                    data = response.body()
                    val convertedData = convertApiDataToProductList(data)
                    productList = convertedData
                    var counter = 0
                    if (!data.isNullOrEmpty()) { // data is NOT null or empty
                        val dbProds =
                            productList?.map {
                                counter += 1
                                DBProduct(
                                    counter.toString(),
                                it.name, it.expiryDate, it.price, it.type.toString())
                            }
                        val database = productListFragment.getDatabase()?.productDao()
                        if (database != null) {
                            database.deleteAllProducts()
                            dbProds?.map { database.insert(it) }
                            //Log.i("Database updated", database.getAllProducts().toString())
                        }
                    }
                } else { // failed api call
                    Log.i("Failed API Call:", "")
                }
            }

            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = false
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                if (!productList.isNullOrEmpty()) {
                    productBinding.noProductsTextView.visibility = View.INVISIBLE
                    productBinding.refreshButton.visibility = View.INVISIBLE
                } else {
                    productBinding.noProductsTextView.visibility = View.VISIBLE
                    productBinding.refreshButton.visibility = View.VISIBLE
                }
            }
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