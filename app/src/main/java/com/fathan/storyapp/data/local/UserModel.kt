package com.fathan.storyapp.data.local

data class UserModel(
    val userId: String,
    val name: String,
    val token : String,
    val isLogin: Boolean
)