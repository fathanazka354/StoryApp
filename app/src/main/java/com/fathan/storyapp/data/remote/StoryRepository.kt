package com.fathan.storyapp.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.fathan.storyapp.data.local.StoryDatabase
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.helper.UserPreference

class StoryRepository(private val quoteDatabase:StoryDatabase, private val apiService: ApiService,private val userPreference: UserPreference)
{
    fun getListStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            remoteMediator = StoryRemoteMediator(quoteDatabase, apiService,userPreference),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                quoteDatabase.storyDao().getAllQuote()
            }
        ).liveData
    }
}