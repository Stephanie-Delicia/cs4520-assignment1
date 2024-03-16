package com.cs4520.assignment1

class ApiProduct {
    private var name: String? = null
    private var type: String? = null
    private var expiryDate: String? = null
    private var price: Double? = null

    fun getName() : String? {
        return name
    }

    fun getType() : String? {
        return type
    }

    fun getExpiryDate() : String? {
        return expiryDate
    }

    fun getPrice() : Double? {
        return price
    }
}