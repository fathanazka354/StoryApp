package com.fathan.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.databinding.ActivityDetailStoryBinding


class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem
        Log.d("DetailStoryActivity: ",data.toString())
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
            Glide.with(this)
                .load(data.photoUrl)
                .into(binding.imageStory)
        binding.apply {
            tvDate.setText(data.createdAt)
            tvName.setText(data.name)
            tvDeskripsi.setText(data.description)
        }

    }
    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}