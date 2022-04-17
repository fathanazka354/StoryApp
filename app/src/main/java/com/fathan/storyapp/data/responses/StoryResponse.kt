package com.fathan.storyapp.data.responses

import com.google.gson.annotations.SerializedName

data class StoryResponse (

    @field:SerializedName("listStory")
    val listStory:ArrayList<ListStoryItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
