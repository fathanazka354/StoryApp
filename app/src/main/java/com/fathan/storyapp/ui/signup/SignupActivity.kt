package com.fathan.storyapp.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fathan.storyapp.R
import com.fathan.storyapp.databinding.ActivitySignupBinding
import com.fathan.storyapp.ui.login.LoginActivity


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        setupAction()
        playActivity()

        binding.masuk.setOnClickListener {
            Intent(this,LoginActivity::class.java).let {
                startActivity(it)
            }
        }

        signupViewModel.isLoading.observe(this,{
            isLoading(it)
            isLoadingIntent(it)
        })

        signupViewModel.APImess.observe(this,{mess ->
            Toast.makeText(this,mess,Toast.LENGTH_SHORT).show()
        })

        signupViewModel.isNameEmpty.observe(this,{
            if(it) binding.nameEditTextLayout.setError(getString(R.string.required_name))
        })

        signupViewModel.isEmailEmpty.observe(this,{
            if(it) binding.etEmail.setError(getString(R.string.required_email))
        })

        signupViewModel.isPasswordEmpty.observe(this,{
            if(it) binding.etPassword.setError(getString(R.string.required_password))
        })

        signupViewModel.isEmailValid.observe(this,{
            if(!it) binding.etEmail.setError(getString(R.string.email_is_invalid)) else binding.etEmail.setError(null)
        })
        signupViewModel.isPasswordValid.observe(this,{
            if(!it) binding.etPassword.setError(getString(R.string.password_min_6_char)) else binding.etEmail.setError(null)
        })
    }


    private fun playActivity() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA,0f, 1f).setDuration(500)
        val nameTV = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA,0f, 1f).setDuration(500)
        val nameET = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA,0f, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,0f, 1f).setDuration(500)
        val emailET = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,0f, 1f).setDuration(500)
        val passwordTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,0f, 1f).setDuration(500)
        val passwordET = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,0f, 1f).setDuration(500)
        val btnSignup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA,0f, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, nameTV,nameET, emailTV,emailET,passwordTV,passwordET,btnSignup)
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
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditTextLayout.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            signupViewModel.signUp(name,email,password)

        }
    }

    private fun isLoading(value:Boolean){
        if (value){
            binding.signupButton.isEnabled = false
            binding.pgSignup.visibility = View.VISIBLE
        }else{
            binding.signupButton.isEnabled = true
            binding.pgSignup.visibility = View.GONE
        }
    }

    private fun isLoadingIntent(value: Boolean){
        if (value){return}
        else{
            Intent(this,LoginActivity::class.java).let {
                startActivity(it)
            }
        }
    }
    companion object{
        val TAG = "ActivitySignUp"
    }
}