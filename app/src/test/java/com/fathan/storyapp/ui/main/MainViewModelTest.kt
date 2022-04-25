package com.fathan.storyapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.fathan.storyapp.DataDummy
import com.fathan.storyapp.MainCoroutineRule
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.utils.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Story Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val dummyStory = DataDummy.generateDummyStoryList()
        val data = PageDataTestSources.getItem(dummyStory)
        val story = MutableLiveData<PagingData<ListStoryItem>>()
        story.value = data

        Mockito.`when`(mainViewModel.listStory).thenReturn(story)
        val dataActual = mainViewModel.listStory.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        differ.submitData(dataActual)

        advanceUntilIdle()
        Mockito.verify(mainViewModel).listStory
        Truth.assertThat(differ.snapshot()).isNotNull()
        Truth.assertThat(dummyStory.size).isEqualTo(differ.snapshot().size)
        Truth.assertThat(dummyStory[0].name).isEqualTo(differ.snapshot()[0]?.name)
    }

    class PageDataTestSources : PagingSource<Int,LiveData<List<ListStoryItem>>>(){
        companion object{
            fun getItem(data: List<ListStoryItem>):PagingData<ListStoryItem>{
                return PagingData.from(data)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
            return LoadResult.Page(emptyList(),0,1)
        }

    }

    val listUpdateCallback = object : ListUpdateCallback{
        override fun onInserted(position: Int, count: Int) {
        }

        override fun onRemoved(position: Int, count: Int) {
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
        }
    }

}