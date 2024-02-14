package com.cs4520.assignment1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val productList: List<Product> = entireDatasetConverted
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = productList[position].name
        val expiry = productList[position].expiryDate
        if (expiry.isNullOrEmpty()) { // if no expiration date is given, make component invisible
            holder.itemPrice.isInvisible = true
            holder.itemExpiration.text = "$" + productList[position].price.toString()
        } else {
            holder.itemExpiration.text = productList[position].expiryDate
            holder.itemPrice.text = "$" + productList[position].price.toString()
        }
        holder.itemImage.setImageResource(productList[position].imageForDisplay())
        holder.itemBackground.setCardBackgroundColor(Color.parseColor(productList[position].backgroundColor()))
    }
    override fun getItemCount(): Int {
        return productList.size
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

}