package com.fathan.storyapp

import com.fathan.storyapp.data.responses.ListStoryItem

object DataDummy {
    fun generateDummyStoryList(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val news = ListStoryItem(
                "$i",
                "Fathan Azka $i",
                "$i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022",
                123.2,
                123.2
            )
            storyList.add(news)
        }
        return storyList
    }
}