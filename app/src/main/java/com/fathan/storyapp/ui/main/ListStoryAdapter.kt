package com.fathan.storyapp.ui.main

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fathan.storyapp.data.responses.ListStoryItem
//import com.fathan.storyapp.data.responses.ListListStoryItemItemDB
import com.fathan.storyapp.databinding.ItemStoryBinding
import com.fathan.storyapp.ui.detail.DetailStoryActivity

class ListAdapter : PagingDataAdapter<ListStoryItem, ListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.apply {
                val dateList = data.createdAt.split("T")
            val dateListListStoryItem = dateList[0]
            binding.apply {
                name.setText(data.name)
                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .centerCrop()
                    .into(imgStory)
                date.setText(dateListListStoryItem)
                val listListStoryItemDetail = ListStoryItem(data.id,data.name,data.description,data.photoUrl,data.createdAt,data.lat,data.lon)
                Log.d("data:",listListStoryItemDetail.toString())

                itemView.setOnClickListener {

                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)

                    intent.putExtra(DetailStoryActivity.EXTRA_DATA,data)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgStory,"imageListStoryItem"),
                            Pair(name,"nameListStoryItem"),
                            Pair(date,"dateListStoryItem"),
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }

            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
//class ListListStoryItemAdapter :
//    PagingDataAdapter<ListListStoryItemItem, ListListStoryItemAdapter.MyViewHolder>(DIFF_CALLBACK) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemListStoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val data = getItem(position)
//        if (data != null) {
//            holder.bind(data)
//        }
//    }
//
//    class MyViewHolder(private val binding: ItemListStoryItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: ListListStoryItemItem) {
//            val dateList = data.createdAt.split("T")
//            val dateListListStoryItem = dateList[0]
//            binding.apply {
//                name.setText(data.name)
//                Glide.with(itemView.context)
//                    .load(data.photoUrl)
//                    .into(imgListStoryItem)
//                date.setText(dateListListStoryItem)
//                val listListStoryItemDetail = ListListStoryItemItem(data.id,data.name,data.description,data.photoUrl,data.createdAt,data.lat,data.lon)
//                Log.d("data:",listListStoryItemDetail.toString())
//
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailListStoryItemActivity::class.java)
//
////                    intent.putExtra(DetailListStoryItemActivity.EXTRA_DATA,data)
//
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(imgListStoryItem,"imageListStoryItem"),
//                            Pair(name,"nameListStoryItem"),
//                            Pair(date,"dateListStoryItem"),
//                        )
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
//                }
//            }
//        }
//    }
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListListStoryItemItem>() {
//            override fun areItemsTheSame(oldItem: ListListStoryItemItem, newItem: ListListStoryItemItem): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: ListListStoryItemItem, newItem: ListListStoryItemItem): Boolean {
//                return oldItem.id == newItem.id
//            }
//        }
//    }
//}