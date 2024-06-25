package com.example.yesnightmarket.Data

data class Store (
    var name: String="",
    val nightMarket: String="",
    var description: String="",
    var phone: String="",
    var openingHours: String="",
    var address: String="",
    var imageUrl: String="",
    val score: Double=0.0,
    val likes: Int=0,

    )