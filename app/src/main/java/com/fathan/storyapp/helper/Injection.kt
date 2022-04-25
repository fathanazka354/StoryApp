package com.fathan.storyapp.helper

import android.content.Context
import com.fathan.storyapp.data.local.StoryDatabase
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.remote.StoryRepository

object Injection {
    fun provideRepository(context: Context,userPreference: UserPreference): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService,userPreference)
    }
}