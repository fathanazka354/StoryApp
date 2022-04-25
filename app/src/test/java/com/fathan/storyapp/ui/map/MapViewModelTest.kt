package com.fathan.storyapp.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fathan.storyapp.DataDummy
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.data.responses.LoginRes
import com.fathan.storyapp.utils.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mapViewModel: MapViewModel

    @Test
    fun `when Story should return the right data`(){
        val listStoryDummy = DataDummy.generateDummyStoryList()
        val expectedData = MutableLiveData<List<ListStoryItem>>()
        expectedData.value = listStoryDummy

        Mockito.`when`(mapViewModel.listStory).then { expectedData }

        val actualData = mapViewModel.listStory.getOrAwaitValue()

        Mockito.verify(mapViewModel).listStory
        Truth.assertThat(actualData).isEqualTo(expectedData.value)
    }

    @Test
    fun `verify function of getAllDataLocation is works`(){
        val token = "abcdefg"
        val loginRes = LoginRes("fathan","fathan","fathan")
        val expectedData = MutableLiveData<LoginRes>()
        expectedData.value = loginRes

        mapViewModel.getAllDataLocation(token)

        Mockito.verify(mapViewModel).getAllDataLocation(token)
    }
}