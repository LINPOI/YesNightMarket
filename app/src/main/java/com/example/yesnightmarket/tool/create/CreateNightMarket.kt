package com.example.yesnightmarket.tool.create

import com.example.yesnightmarket.Data.ColumnDefinition
import com.example.yesnightmarket.Data.CreateTableRequest
import com.example.yesnightmarket.Data.ForeignKey

import com.example.yesnightmarket.tool.http.Retrofit
import com.example.yesnightmarket.tool.http.SQLTYPE

import com.example.yesnightmarket.tool.custmozed.log
import com.example.yesnightmarket.tool.custmozed.loge
import com.google.gson.Gson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun CreateNightMarket(nightMarketName: String) {

    val columns = mapOf(
        "name" to SQLTYPE.STRING.SQL_TYPE,
        "address" to SQLTYPE.STRING.SQL_TYPE,
        "phone" to SQLTYPE.STRING.SQL_TYPE,
        "openingHours" to SQLTYPE.STRING.SQL_TYPE,
        "imageUrl" to SQLTYPE.STRING.SQL_TYPE,
        "score" to SQLTYPE.DOUBLE.SQL_TYPE,
        "likes" to SQLTYPE.INT.SQL_TYPE
    )
    log(columns.toString())
    val request = CreateTableRequest(
        tableName = nightMarketName,
        columns = columns,
        primaryKey = listOf("name")
    )
    log(request.toString())
    createTable(request)
    CreateNightmarketMessage(nightMarketName, "name")
}

fun CreateNightmarketMessage(nightMarketName: String, column: String) {

    val columns = mapOf(
        "author" to SQLTYPE.STRING.SQL_TYPE,
        "content" to SQLTYPE.STRING.SQL_TYPE,
        "nightMarket" to ColumnDefinition(
            type = SQLTYPE.STRING.SQL_TYPE,
            foreign = ForeignKey(
                table = nightMarketName,
                column = column

            )
        )
    )
    val request = CreateTableRequest(
        tableName = "${nightMarketName}_Message",
        columns = columns,
        primaryKey = listOf("author")
    )
    createTable(request)
}

fun CreateStoreMessage(nightMarketName: String, storeName: String, column: String) {
    val table = "${nightMarketName}_${storeName}"
    val columns = mapOf(
        "author" to SQLTYPE.STRING_PRIMARY_KEY.SQL_TYPE
    ,
    "content" to SQLTYPE.STRING.SQL_TYPE,
    "store" to ColumnDefinition(
            type = SQLTYPE.STRING.SQL_TYPE,
    foreign = ForeignKey(
        table = table,
        column = column
    )
    )
    )
    val request = CreateTableRequest(
        tableName = "${table}_Message",
        columns = columns
    )
    createTable(request)
}

fun CreateStores(nightMarketName: String) {
    val columns = mapOf(
        "name" to SQLTYPE.STRING.SQL_TYPE,
    "description" to SQLTYPE.STRING.SQL_TYPE,
    "openingHours" to SQLTYPE.INT.SQL_TYPE,
    "address" to SQLTYPE.STRING.SQL_TYPE,
    "imageUrl" to SQLTYPE.STRING.SQL_TYPE,
    "score" to SQLTYPE.DOUBLE.SQL_TYPE,
    "likes" to SQLTYPE.INT.SQL_TYPE
    )

    val request = CreateTableRequest(
        tableName = nightMarketName,
        columns = columns,
        primaryKey = listOf("name", "nightMarket")
    )
    createTable(request)

}

fun CreateCommodity(
    nightMarketName: String,
    storeName: String,
    commodityName: String,
    column: String
) {
    val table = "${nightMarketName}_${storeName}"
    val tableName = "${table}_commodity"
    val columns = mapOf(
        "name" to SQLTYPE.STRING.SQL_TYPE
    ,
    "price" to SQLTYPE.DOUBLE.SQL_TYPE,
    "score" to SQLTYPE.DOUBLE.SQL_TYPE,
    "imageUrl" to SQLTYPE.STRING.SQL_TYPE,
    "store" to ColumnDefinition( type =
        SQLTYPE.STRING.SQL_TYPE, foreign = ForeignKey(table = table, column = column)),
    )
    val request = CreateTableRequest(
        tableName = tableName,
        columns = columns,
        primaryKey = listOf("name", "store")
    )
    createTable(request)
}

fun createTable(request: CreateTableRequest) {
    val call = Retrofit.apiService.createTable(request)
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                log(response.body() ?: "Success")
            } else {
                loge("Error: ${response.code()}:$response")
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            loge("Failure: ${t.message}")
        }
    })
}