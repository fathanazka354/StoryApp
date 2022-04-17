package com.fathan.storyapp.data.responses

import com.google.gson.annotations.SerializedName

data class LoginRes(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)