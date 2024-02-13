package com.cs4520.assignment1

public val productsDataset = listOf(
    listOf("Treadmill", "Equipment", null, 32),
    listOf("Banana", "Food", "2024-02-29", 29),
    listOf("Dumbbells", "Equipment", null, 45),
    listOf("Apple", "Food", "2024-03-10", 20),
    listOf("Stationary Bike", "Equipment", null, 50),
    listOf("Orange", "Food", "2024-03-05", 25),
    listOf("Yoga Mat", "Equipment", null, 15),
    listOf("Grapes", "Food", "2024-02-02", 18),
    listOf("Resistance Bands", "Equipment", null, 22),
    listOf("Kiwi", "Food", "2024-01-29", 30),
    listOf("Elliptical Machine", "Equipment", null, 55),
    listOf("Strawberries", "Food", "2024-03-08", 28),
    listOf("Weight Bench", "Equipment", null, 40),
    listOf("Watermelon", "Food", "2024-03-12", 15),
    listOf("Jump Rope", "Equipment", null, 10),
    listOf("Blueberries", "Food", "2024-04-05", 22),
    listOf("Kettlebell", "Equipment", null, 35),
    listOf("Mango", "Food", "2024-04-10", 33),
    listOf("Rowing Machine", "Equipment", null, 48),
    listOf("Pineapple", "Food", "2024-03-20", 26),
    listOf("Pull-Up Bar", "Equipment", null, 30),
    listOf("Peach", "Food", "2024-04-15", 23),
    listOf("Medicine Ball", "Equipment", null, 18),
    listOf("Cherry", "Food", "2024-04-08", 35),
    listOf("Foam Roller", "Equipment", null, 20),
    listOf("Papaya", "Food", "2024-04-18", 32),
    listOf("Balance Ball", "Equipment", null, 25),
    listOf("Pear", "Food", "2024-04-20", 27),
    listOf("Step Platform", "Equipment", null, 15),
    listOf("Plum", "Food", "2024-04-28", 19),
    listOf("Battle Ropes", "Equipment", null, 42),
    listOf("Apricot", "Food", "2024-04-25", 21),
    listOf("Trampoline", "Equipment", null, 38),
    listOf("Raspberry", "Food", "2024-05-02", 24),
    listOf("Gymnastic Rings", "Equipment", null, 50),
    listOf("Blackberry", "Food", "2024-05-08", 29),
)

public val entireDatasetConverted : List<Product> = productsDataset.map{ if (it[1] == "Food") {
    Product.FoodProduct(it[0].toString(), it[2]?.toString(), it[3] as Int, ProductType.FOOD)
} else {
    Product.EquipmentProduct(it[0].toString(), it[2]?.toString(), it[3] as Int, ProductType.EQUIPMENT)
} }

public val foodDataset: List<Product.FoodProduct>
= productsDataset.filter{it[1] == "Food"}.toSet().map{
    Product.FoodProduct(it[0].toString(), it[2]?.toString(), it[3] as Int, ProductType.FOOD)}

public val equipmentProductDataset : List<Product.EquipmentProduct>
= productsDataset.filter{it[1] == "Equipment"}.toSet().map{
    Product.EquipmentProduct(it[0].toString(), it[2]?.toString(), it[3] as Int, ProductType.EQUIPMENT)}
enum class ProductType {
    EQUIPMENT, FOOD
}
interface IProduct {            // Interface for a ticket
    abstract val name: String // all tickets should have a price
    abstract val expiryDate: String? // optional
    abstract val price: Int
    abstract val type: ProductType
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
                info = "Exp. date: " + expiryDate + "\n"
            }
            info = info + "$" + price.toString()
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
                info = "Exp. date: " + expiryDate + "\n"
            }
            info = info + "$" + price.toString()
            return info
        }
    }
}

fun main() {
    println(foodDataset.map{it.price}.toString())
}