package com.example.yesnightmarket.tool.http


import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
const val URL = "http://192.168.50.80:3000/"
object Retrofit {

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // 攔截器
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    //
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    //

    // 可以使用 Moshi 的 JsonReader 設置 lenient 模式
    val lenientMoshi = moshi.newBuilder()
        .add(KotlinJsonAdapterFactory())
        .build()
    //

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory( MoshiConverterFactory.create(lenientMoshi))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}