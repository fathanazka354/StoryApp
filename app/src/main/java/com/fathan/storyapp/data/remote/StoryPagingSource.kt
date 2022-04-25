package com.fathan.storyapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.helper.UserPreference
import kotlinx.coroutines.flow.first
//import javax.inject.Inject

class StoryPagingSource(private val apiService: ApiService,private val userPreference: UserPreference) : PagingSource<Int, ListStoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val token = userPreference.getUser().first().token
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStory(token,position, params.loadSize).listStory
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
//            Log.d()
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}