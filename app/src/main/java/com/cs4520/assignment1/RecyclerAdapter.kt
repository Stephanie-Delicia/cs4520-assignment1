package com.cs4520.assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val foodList: List<Product.FoodProduct> = foodDataset
    val equpimentList: List<Product.EquipmentProduct> = equipmentProductDataset
    val productList: List<Product> = foodList + equpimentList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = productList[position].name
        holder.itemDetail.text = productList[position].price.toString()
        holder.itemImage.setImageResource(productList[position].imageForDisplay())
    }
    override fun getItemCount(): Int {
        return productList.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
        }
    }

}