package com.fathan.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fathan.storyapp.data.responses.LoginRes
import com.fathan.storyapp.data.responses.StoryResponse
import com.fathan.storyapp.utils.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.fathan.storyapp.data.Result

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    @Test
    fun `when Get loginResult Should Not Null and Return Success`(){
        val loginResult = LoginRes("fathan","fathan","fathan")
        val dataExpected = MutableLiveData<LoginRes>()
        dataExpected.value = loginResult

        Mockito.`when`(loginViewModel.dataUser).then { dataExpected }
        val actualDAta = loginViewModel.dataUser.getOrAwaitValue()

        Mockito.verify(loginViewModel).dataUser
        Truth.assertThat(actualDAta).isEqualTo(dataExpected.value)
    }

    @Test
    fun `verify loading is return data true or stop`(){
        val dataExpected = MutableLiveData<Boolean>()
        dataExpected.value = true

        Mockito.`when`(loginViewModel.isLoading).then { dataExpected }

        val actualData = loginViewModel.isLoading.getOrAwaitValue ()

        Mockito.verify(loginViewModel).isLoading
        Truth.assertThat(actualData).isEqualTo(dataExpected.value)
    }

    @Test
    fun `when user input true data`(){
        val email = "fathan"
        val password = "fathan"

        loginViewModel.loginUser(email,password)
        Mockito.verify(loginViewModel).loginUser(email, password)
    }
}