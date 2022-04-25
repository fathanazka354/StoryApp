package com.fathan.storyapp.ui.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.data.responses.LoginRes
import com.fathan.storyapp.ui.signup.SignupViewModel
import com.fathan.storyapp.utils.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addStoryViewModel: AddStoryViewModel

    @Test
    fun `when getUser should not null and success`(){
        val userDummy = UserModel("fathan","fathan","fathan",true)
        val expectedData = MutableLiveData<UserModel>()
        expectedData.value = userDummy

        Mockito.`when`(addStoryViewModel.getUser()).then { expectedData }
        val actualData = addStoryViewModel.getUser().getOrAwaitValue()

        Mockito.verify(addStoryViewModel).getUser()
        Truth.assertThat(actualData).isNotNull()
        Truth.assertThat(expectedData.value).isEqualTo(actualData)
    }
}