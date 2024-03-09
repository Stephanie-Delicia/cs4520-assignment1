package com.cs4520.assignment1

class ApiProduct {
    private var name: String? = null
    private var type: String? = null
    private var expiryDate: String? = null
    private var price: Double? = null

    fun getName() : String? {
        return name
    }

    fun setName(newName : String) {
        name = newName
    }

    fun getType() : String? {
        return type
    }

    fun setType(newType : String) {
        type = newType
    }

    fun getExpiryDate() : String? {
        return expiryDate
    }

    fun setExpiryDate(newExp : String) {
        expiryDate = newExp
    }

    fun getPrice() : Double? {
        return price
    }

    fun setPrice(newPrice : Double) {
        price = newPrice
    }
}