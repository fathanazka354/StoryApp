package com.fathan.storyapp.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.helper.UserPreference

class AddStoryViewModel(private val pref: UserPreference) :ViewModel(){
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}