package com.fathan.storyapp.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.data.responses.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _APImessage = MutableLiveData<String>()
    val APImessage: LiveData<String> = _APImessage

    val listUser = MutableLiveData<ArrayList<ListStoryItem>>()


    fun getListStory(token: String){
        val storyList = ArrayList<ListStoryItem>()
        val client = ApiConfig.getApiService().getStories("Bearer "+token)

        _isLoading.value = true
        client.enqueue(object: Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    Log.d("MainActivity","Token: "+token)
                    listUser.postValue(responseBody.listStory)
                    listUser.value = storyList
                }else{
                    Log.d("MainActivity","Token: "+token)
                    _isLoading.value = false
                    _APImessage.value = responseBody?.message
                    Log.e("MainActivity","onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.d("MainActivity","Token: "+token)
                _isLoading.value = false
                _APImessage.value = t.message
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}