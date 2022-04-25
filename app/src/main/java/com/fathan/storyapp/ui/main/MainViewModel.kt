package com.fathan.storyapp.ui.main

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.remote.StoryRepository
import com.fathan.storyapp.data.responses.ListStoryItem
//import com.fathan.storyapp.data.responses.ListStoryItemDB
import com.fathan.storyapp.data.responses.StoryResponse
import com.fathan.storyapp.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 class MainViewModel(private val pref: UserPreference, quoteRepository: StoryRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _APImessage = MutableLiveData<String>()
    val APImessage: LiveData<String> = _APImessage

    val listUser = MutableLiveData<List<ListStoryItem>>()

    val listStory: LiveData<PagingData<ListStoryItem>> =
        quoteRepository.getListStory().cachedIn(viewModelScope)

    fun getListStory(token: String){
        wrapEspressoIdlingResource {
            val storyList = ArrayList<ListStoryItem>()
    //        this.token_header = token
            val client = ApiConfig.getApiService().getStories("Bearer "+token,1)

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