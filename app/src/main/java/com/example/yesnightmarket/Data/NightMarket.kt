package com.example.yesnightmarket.Data

data class NightMarket(
    var name: String="",
    var address: String="",
    var phone: String="",
    var openingHours: String="",
    var imageUrl: String="",
    val score: Double=0.0,
    val likes: Int=0,
)
