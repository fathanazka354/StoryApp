package com.fathan.storyapp.ui.signup

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fathan.storyapp.data.responses.LoginRes
import com.fathan.storyapp.utils.getOrAwaitValue
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import com.google.common.truth.Truth

@RunWith(MockitoJUnitRunner::class)
class SignupViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var signupViewModel: SignupViewModel
    @Test
    fun `when filled the right data`(){
        val dataExpected = MutableLiveData<Boolean>()
        dataExpected.value = true

        Mockito.`when`(signupViewModel.isLoading).thenReturn(dataExpected)

        val data = signupViewModel.isLoading.getOrAwaitValue()

        Mockito.verify(signupViewModel).isLoading
        Truth.assertThat(data).isEqualTo(dataExpected.value)
    }

    @Test
    fun `verify registry is correct`(){
        val username = "fathan"
        val userId = "abc"
        val token = "abc"
        val loginResponse = LoginRes(userId,username,token)
        val dataExpected = MutableLiveData<LoginRes>()
        dataExpected.value = loginResponse

        signupViewModel.signUp(username,userId,token)

        Mockito.verify(signupViewModel).signUp(username,userId,token)
    }

    @Test
    fun `verify validation is empty data`(){
        val username = ""
        val userId = "abc"
        val token = "abc"
        val loginResponse = LoginRes(userId,username,token)
        val dataExpected = MutableLiveData<LoginRes>()
        dataExpected.value = loginResponse

        signupViewModel.formNotEmpty(username,userId,token)

        Mockito.verify(signupViewModel).formNotEmpty(username,userId,token)
    }

}