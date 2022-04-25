package com.fathan.storyapp.ui.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fathan.storyapp.R
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.responses.FileUploadResponse
import com.fathan.storyapp.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading :LiveData<Boolean> = _isLoading

    private val _isNameEmpty = MutableLiveData<Boolean>()
    val isNameEmpty : LiveData<Boolean> = _isNameEmpty

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid : LiveData<Boolean> = _isEmailValid

    private val _isEmailEmpty = MutableLiveData<Boolean>()
    val isEmailEmpty : LiveData<Boolean> = _isEmailEmpty

    private val _isPasswordEmpty = MutableLiveData<Boolean>()
    val isPasswordEmpty :LiveData<Boolean> = _isPasswordEmpty

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid :LiveData<Boolean> = _isPasswordValid

    private val _APImess = MutableLiveData<String>()
    val APImess :LiveData<String> = _APImess

    private fun passwordValidation(pass: String):Boolean{
        return pass.length >= 6
    }

    private fun emailValidation(email: String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun formNotEmpty(name:String, password:String,email:String):Boolean{
        var isNotEmpty = true

        if (name.isEmpty()){
            _isNameEmpty.value = true
            isNotEmpty = false
        }else if (password.isEmpty()){
            _isPasswordEmpty.value = true
            isNotEmpty = false
        }else if(email.isEmpty()){
            _isEmailEmpty.value = true
            isNotEmpty = false
        }

        return isNotEmpty
    }

    fun formValidation(name: String,pass: String,email: String):Boolean{
        var isValid = true

        if (formNotEmpty(name,pass,email)){
            if (!emailValidation(email)){
                _isEmailValid.value = false
                isValid = false
            }else if(!passwordValidation(pass)){
                _isPasswordValid.value = false
                isValid = false
            }
        }
        else{ isValid = false }

        return isValid
    }

    fun signUp(name: String,email: String,pass: String){
        if (formValidation(name,pass,email)){
            wrapEspressoIdlingResource {
                val client = ApiConfig.getApiService().postRegister(name,email,pass)
                _isLoading.value = true
                client.enqueue(object : Callback<FileUploadResponse>{
                    override fun onResponse(
                        call: Call<FileUploadResponse>,
                        response: Response<FileUploadResponse>
                    ) {
                        Log.d("SignupViewModel:","ResponseBody")

                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null){
                        Log.d("SignupViewModel:","ResponseBodySuccess")
                            val error = responseBody.error
                            if (!error){
                        Log.d("SignupViewModel:","ResponseBodySuccessBanget")
                                _isLoading.value = false
                                _APImess.value = responseBody.message
                            }else{
                                _isLoading.value = true
                                _APImess.value = "Failed create user"
                            }
                        }else{
                        Log.d("SignupViewModel:","ResponseBodySuccessTapiSudahDibuat")
                            _isLoading.value = false
                            _APImess.value = R.string.email_has_been_created.toString()
                            Log.e(SignupActivity.TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                        _isLoading.value = true
                        _APImess.value = t.message
                            Log.e(SignupActivity.TAG, "onFailure: ${t.message}")
                    }
                })
            }
        }
    }
}