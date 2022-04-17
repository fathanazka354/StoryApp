package com.fathan.storyapp.ui.main

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fathan.storyapp.data.responses.ListStoryItem
import com.fathan.storyapp.databinding.ItemStoryBinding
import com.fathan.storyapp.ui.detail.DetailStoryActivity

class ListStoryAdapter(private val listStory : ArrayList<ListStoryItem>):RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListViewHolder {
        val binding =ItemStoryBinding.inflate(LayoutInflater.from(view.context),view,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    inner class ListViewHolder(private var binding:ItemStoryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem:ListStoryItem){
            val dateList = listStoryItem.createdAt.split("T")
            val dateListStory = dateList[0]
            binding.apply {
                name.setText(listStoryItem.name)
                Glide.with(itemView.context)
                    .load(listStoryItem.photoUrl)
                    .into(imgStory)
                date.setText(dateListStory)
                val listStoryDetail = ListStoryItem(listStoryItem.id,listStoryItem.name,listStoryItem.description,listStoryItem.photoUrl,listStoryItem.createdAt,listStoryItem.lat,listStoryItem.lon)
                Log.d("listStoryItem:",listStoryDetail.toString())

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_DATA, listStoryItem)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgStory,"imageStory"),
                            Pair(name,"nameStory"),
                            Pair(date,"dateStory"),
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }
}