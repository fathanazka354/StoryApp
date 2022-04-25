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
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathan.storyapp.R
import com.fathan.storyapp.helper.UserPreference
import com.fathan.storyapp.databinding.ActivityMainBinding
import com.fathan.storyapp.ui.ViewModelFactoryMain
import com.fathan.storyapp.ui.addstory.AddStoryActivity
import com.fathan.storyapp.ui.login.LoginActivity
import com.fathan.storyapp.ui.map.MapsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this, ViewModelFactoryMain(this,UserPreference.getInstance(dataStore))).get(
            MainViewModel::class.java
        )
//        mainViewModel.getUser().observe(this, { user ->
//            if (user.isLogin){
//                Log.d("MainActivity: ",user.token)
//                token = "Bearer " + user.token
//                supportActionBar?.title = user.name
//            } else {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//        })

        binding.btnMap.setOnClickListener {
            Intent(this,MapsActivity::class.java).let {
                startActivity(it)
            }
        }

        mainViewModel.listUser.observe(this,{
            setAdapter()
        })

        mainViewModel.getUser().observe(this,{user->
            Log.d("ActivityMain","isLogin: ${user.isLogin}")
            if(user.isLogin){
                EXTRA_TOKEN_MAIN = user.token
                user.token.let { mainViewModel.getListStory(user.token) }
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        mainViewModel.isLoading.observe(this,{
            isLoading(it)
        })

        mainViewModel.APImessage.observe(this,{
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })

    }

    private fun setAdapter(){
        val adapter = ListAdapter()
        lifecycleScope.launch {
            adapter.loadStateFlow.collect  {
                isLoading(it.refresh is LoadState.Loading)
            }
        }
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
        }
        mainViewModel.listStory.observe(this, {
            Log.d("MainActivity ","list data $it ")
            adapter.submitData(lifecycle, it)
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
        var EXTRA_TOKEN_MAIN : String? = null
    }

}