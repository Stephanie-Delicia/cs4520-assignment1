package com.cs4520.assignment1
enum class ProductType {
    Equipment, Food
}
interface IProduct {            // Interface for a ticket
    val name: String // all tickets should have a price
    val expiryDate: String? // optional
    val price: Int
    val type: ProductType
    fun backgroundColor() : String
    fun imageForDisplay() : Int
    fun printInfo() : String
}
sealed class Product : IProduct { // abstract class
    class FoodProduct(
        override val name: String,
        override val expiryDate: String?,
        override val price: Int,
        override val type: ProductType
    ) : Product() {

        override fun backgroundColor(): String {
            return "#FFD965"
        }
        override fun imageForDisplay(): Int {
            return R.drawable.food
        }
        override fun printInfo(): String {
            var info = ""
            if (expiryDate != null) {
                info = "Exp. date: $expiryDate\n"
            }
            info = "$info$$price"
            return info
        }
    }

    class EquipmentProduct(
        override val name: String,
        override val expiryDate: String?,
        override val price: Int,
        override val type: ProductType
    ) : Product() {
        override fun backgroundColor(): String {
            return "#E06666"
        }
        override fun imageForDisplay(): Int {
            return R.drawable.equipment
        }

        override fun printInfo(): String {
            var info = ""
            if (expiryDate != null) {
                info = "Exp. date: $expiryDate\n"
            }
            info = "$info$$price"
            return info
        }
    }
}