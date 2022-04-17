package com.fathan.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fathan.storyapp.R
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.data.local.UserModel
import com.fathan.storyapp.databinding.ActivityLoginBinding
import com.fathan.storyapp.ui.ViewModelFactory
import com.fathan.storyapp.ui.main.MainActivity
import com.fathan.storyapp.ui.signup.SignupActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupViewModel()

        loginViewModel.isLoading.observe(this,{
            isLoading(it)
        })

        loginViewModel.APImessage.observe(this,{message->
            Toast.makeText(this@LoginActivity,message, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.isEmailEmpty.observe(this,{
            if(it) binding.etEmail.setError(getString(R.string.required_email))
        })

        loginViewModel.isPasswordEmpty.observe(this,{
            if(it) binding.etPasswordLogin.setError(getString(R.string.required_password))
        })

        loginViewModel.isEmailValid.observe(this,{
            if(!it) binding.etEmail.setError(getString(R.string.email_is_invalid)) else binding.etEmail.setError(null)
        })
        loginViewModel.isPasswordValid.observe(this,{
            if(!it) binding.etPasswordLogin.setError(getString(R.string.password_min_6_char)) else binding.etEmail.setError(null)
        })
        playActivity()
    }

    private fun playActivity() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA,0f, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,0f, 1f).setDuration(500)
        val passwordTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,0f, 1f).setDuration(500)
        val passwordLayout = ObjectAnimator.ofFloat(binding.etPasswordLogin, View.ALPHA,0f, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA,0f, 1f).setDuration(500)

        binding.daftarTextView.setOnClickListener {
            Intent(this,SignupActivity::class.java).let {
                startActivity(it)
            }
        }

        AnimatorSet().apply {
            playSequentially(title, message, emailTV,emailLayout,passwordTV,passwordLayout,btnLogin)
            start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_bar_awal,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.change_language->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            Log.d("berhasil Login: ",user.token)
            this.user =user

            Log.d("LoginActivity","Token: ${user.token}")
            if(this.user.isLogin){
                Intent(this@LoginActivity,MainActivity::class.java).let {
                    it.putExtra(MainActivity.EXTRA_TOKEN_MAIN,user.token)
                    startActivity(it)
                }
            }
        })
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            loginViewModel.loginUser(this,email,password)
        }
    }

    private fun isLoading(value:Boolean){
        if (value){
            binding.pgSignin.visibility = View.VISIBLE
            binding.loginButton.isEnabled = false
        }else{
            binding.pgSignin.visibility = View.GONE
            binding.loginButton.isEnabled = true
        }
    }
    companion object{
        val TAG ="ActivityLogin"
    }
}