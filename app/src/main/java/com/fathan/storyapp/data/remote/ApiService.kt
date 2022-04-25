package com.fathan.storyapp.data.remote

import com.fathan.storyapp.data.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<FileUploadResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("location")location: Int
    ) : Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStoryWithLocation(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<StoryResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") token:String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse


    @GET("stories")
    fun getListStoryWithLocation(
        @Header("Authorization") auth: String,
        @Query("location")location: Int
    ): Call<StoryResponse>

}