package com.fathan.storyapp.data.responses

import com.google.gson.annotations.SerializedName


data class LoginResponse (

    @field:SerializedName("loginResult")
    val loginResult:LoginRes,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
