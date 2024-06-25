package com.example.YESNightMarket.tool.Check

fun PasswordTextCheck (password: String): Boolean {
    val gmailPattern = Regex("^[a-zA-Z0-9.@$]*$")
    return gmailPattern.matches(password)
}