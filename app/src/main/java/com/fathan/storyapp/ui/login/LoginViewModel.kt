package com.fathan.storyapp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.fathan.storyapp.R
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.data.remote.ApiConfig
import com.fathan.storyapp.data.responses.LoginRes
import com.fathan.storyapp.data.responses.LoginResponse
import com.fathan.storyapp.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _APImessage = MutableLiveData<String>()
    val APImessage: LiveData<String> = _APImessage

    private val _isEmailEmpty = MutableLiveData<Boolean>()
    val isEmailEmpty: LiveData<Boolean> = _isEmailEmpty

    private val _isPasswordEmpty = MutableLiveData<Boolean>()
    val isPasswordEmpty: LiveData<Boolean> = _isPasswordEmpty

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    private val _dataUser = MutableLiveData<LoginRes>()
    val dataUser: LiveData<LoginRes> = _dataUser

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(user:UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }

    private fun emailValidation(email: String) : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun passwordValidation(pass: String) : Boolean{
        return pass.length >= 6
    }

    private fun formNotEmpty(email: String, password: String): Boolean{
        var isNotEmpty = true
        if(email.length == 0){
            _isEmailEmpty.value = true
            isNotEmpty = false
        }
        if(password.length == 0){
            _isPasswordEmpty.value = true
            isNotEmpty = false
        }
        return isNotEmpty
    }
    private fun formValidation(email: String,pass: String):Boolean{
        var isValid = true
        if(formNotEmpty(email,pass)){
            if(!emailValidation(email)){
                _isEmailValid.value = false
                isValid = false
            }else if(!passwordValidation(pass)){
                _isPasswordValid.value = false
                isValid = false
            }
        }else{ isValid = false}
        return isValid
    }

    fun loginUser(email: String, password: String){
        if(formValidation(email,password)) {
            wrapEspressoIdlingResource {
                val client = ApiConfig.getApiService().postLogin(email, password)
                _isLoading.value = true
                Log.d("LoginViewModel:","masukformValidation")
                client.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d("LoginViewModel:","ResponseBody")
                        if (response.isSuccessful && responseBody != null) {
                        Log.d("LoginViewModel:","ResponseBodySuccess")
                            val error = responseBody.error
                            if (!error) {
                                val user = UserModel(
                                    responseBody.loginResult.userId,
                                    responseBody.loginResult.name,
                                    responseBody.loginResult.token,
                                    true
                                )
                                login(user)
                                _dataUser.value = responseBody.loginResult
                                _isLoading.value = false
                                _APImessage.value = responseBody.message
                                Log.e(LoginActivity.TAG,"onSuccess: ${responseBody.message}")
                            } else {
                                _isLoading.value = false
                                _APImessage.value = R.string.login_is_failed.toString()
                            }
                        } else {
                            _isLoading.value = false
                            _APImessage.value = R.string.wrong_email_pass.toString()
                            Log.e(LoginActivity.TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _isLoading.value = false
                        _APImessage.value = t.message
                        Log.e(LoginActivity.TAG, "onFailure: ${t.message}")
                    }

                })
            }
        }
    }
}