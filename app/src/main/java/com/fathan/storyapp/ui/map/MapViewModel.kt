package com.fathan.storyapp.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.data.responses.StoryResponse
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel(private val pref: UserPreference):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _APImessage = MutableLiveData<String>()
    val APImessage: LiveData<String> = _APImessage

    private val _listUser = MutableLiveData<List<ListStoryItem>>()
    val listStory : LiveData<List<ListStoryItem>> get() = _listUser

    fun getAllDataLocation(token:String){
        wrapEspressoIdlingResource {
            val storyList = ArrayList<ListStoryItem>()

            val client = ApiConfig.getApiService().getStories("Bearer "+token,1)

            _isLoading.value = true
            client.enqueue(object : Callback<StoryResponse>{
                override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                    val responseBody = response.body()
                    if(response.isSuccessful && responseBody != null){
                        Log.d("MapsActivity",responseBody.toString())
                        _listUser.postValue(responseBody.listStory)
                        _listUser.value = storyList
                    }else{
                        _isLoading.value = false
                        _APImessage.value = responseBody?.message
                        Log.d("MapsActivity","onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    _isLoading.value = false
                    _APImessage.value = t.message
                    Log.e("MapsActivity", "onFailure: ${t.message}")
                }
            })
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}