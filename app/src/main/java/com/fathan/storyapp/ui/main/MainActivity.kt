package com.fathan.storyapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathan.storyapp.R
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.databinding.ActivityMainBinding
import com.fathan.storyapp.ui.ViewModelFactory
import com.fathan.storyapp.ui.addstory.AddStoryActivity
import com.fathan.storyapp.ui.login.LoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()


        mainViewModel.listUser.observe(this,{
            setAdapter(it)
            isLoading(false)
        })

        mainViewModel.getUser().observe(this,{user->
            Log.d("ActivityMain","isLogin: ${user.isLogin}")
            if(user.isLogin){
                user.token.let { mainViewModel.getListStory(user.token) }
            }
        })

        mainViewModel.isLoading.observe(this,{
            isLoading(it)
        })

        mainViewModel.APImessage.observe(this,{
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })

    }

    private fun setAdapter(listStory : ArrayList<ListStoryItem>){
        val adapter = ListStoryAdapter(listStory)
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this, { user ->
            if (user.isLogin){
                Log.d("MainActivity: ",user.token)
                supportActionBar?.title = user.name
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                mainViewModel.logout()
            }
            R.id.add->{
                mainViewModel.getUser().observe(this,{ user ->
                    Intent(this,AddStoryActivity::class.java).let {
                        it.putExtra(AddStoryActivity.EXTRA_TOKEN,user.token)
                        startActivity(it)
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isLoading(value:Boolean){
        if (value){
            binding.pgMain.visibility = View.VISIBLE
            binding.rvStory.visibility = View.GONE
        }else{
            binding.pgMain.visibility = View.GONE
            binding.rvStory.visibility = View.VISIBLE
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val EXTRA_TOKEN_MAIN = "extra_token"
    }

}