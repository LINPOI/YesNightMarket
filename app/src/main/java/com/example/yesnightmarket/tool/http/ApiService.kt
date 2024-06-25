package com.example.yesnightmarket.tool.http

import com.example.yesnightmarket.Data.CreateTableRequest
import com.example.yesnightmarket.Data.Item
import com.example.yesnightmarket.Data.Member
import com.example.yesnightmarket.Data.MemberResponse
import com.example.yesnightmarket.Data.NightMarket
import com.example.yesnightmarket.Data.PhotoInfoResponse
import com.example.yesnightmarket.Data.ServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ApiService {
        @GET("items")
        suspend fun getItems(): List<Item>

    @GET("members/{gmail}")
    suspend fun getMemberByGmail(@Path("gmail") gmail: String): Member
    @PUT("members/{gmail}")
    suspend fun updateMemberByGmail(@Path("gmail") gmail: String, @Body member: Member): Response<MemberResponse>
    @GET("members")
    suspend fun getMembers(): List<Member>
    @POST("members")
    suspend fun addMember(@Body  member: Member): ServerResponse
    @POST("members/login")
    suspend fun login(@Body member: Member):  Response<ServerResponse>
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): ServerResponse
    @Multipart
    @POST("upload")
    suspend fun uploadImageWithDetails(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("user_id") userId: RequestBody
    ): ServerResponse
    @GET("/nightmarkets")
    suspend fun getNightMarkets(): List<NightMarket>
    @GET("/nightmarkets/{name}")
    suspend fun getNightMarketByName(@Path("name") name: String): NightMarket
    @PUT("/nightmarkets/{name}")
    suspend fun updateNightMarket(@Path("name") name: String, @Body nightMarket: NightMarket): Response<ServerResponse>
    @HEAD("/nightmarkets/{name}")
    suspend fun checkNightMarket(@Path("name") name: String): Response<ServerResponse>
    @POST("/nightmarkets")
    suspend fun addNightMarket(@Body nightMarket: NightMarket):  Response<ServerResponse>



    @POST("/create-table")
    fun createTable(@Body request: CreateTableRequest):  Call<String>


    //舊
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part filePart: MultipartBody.Part,
                            @Part("information") jsonPart: RequestBody
    ): Response<ResponseBody>
    @Streaming
    @GET("photo/get/folder")
    suspend fun getImages(): Response<ResponseBody>
    @GET("photo/get/folder/information")
    suspend fun getImagesInfo(): Response<List<PhotoInfoResponse>>

}
//LaunchedEffect(Unit) {}
// CoroutineScope(Dispatchers.Main).launch {}
// runBlocking {}