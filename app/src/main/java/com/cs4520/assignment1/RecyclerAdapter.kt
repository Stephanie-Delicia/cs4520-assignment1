package com.cs4520.assignment1

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class RecyclerAdapter(progressBar : ProgressBar): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    public var productList: List<Product>? = ArrayList<Product>()
    private var progressBar = progressBar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        Log.i("In", "onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        Log.i("In", "onBindViewHolder")

        // retrieveProductData(progressBar)
        //this.notifyDataSetChanged()
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
    public fun retrieveProductData() {
        progressBar.visibility = View.VISIBLE
        progressBar.isIndeterminate = true
        val service = RetrofitClient.retrofit.create(ApiService::class.java)
        Log.i("Retrieving Data:", "retrieveProductData")
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAllData("2")
            if (response.isSuccessful) {

                Log.i("API Call:", response.toString())
                val data = response.body()
                val convertedData = convertApiDataToProductList(data)
                productList = convertedData
                // notifyDataSetChanged()
                if (!data.isNullOrEmpty()) {
                    Log.i("Actual Data:", data.toString())
                    Log.i("Sample Data:", data[0].getName().toString())
                } else {
                    Log.i("API Call:", "Empty data.")
                }
            } else {
                Log.i("API Call:", "Failed to fetch data.")
            }

            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = false
        }
            Log.i("Finished data fetching:", "")
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
                        ProductType.FOOD
                    )
                } else {
                    Product.EquipmentProduct(
                        it.getName()!!,
                        it.getExpiryDate(),
                        it.getPrice()!!.roundToInt(),
                        ProductType.EQUIPMENT
                    )
                }
            }
        }
        return returnList
    }

}