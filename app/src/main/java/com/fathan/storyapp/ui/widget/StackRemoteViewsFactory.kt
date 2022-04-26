//package com.fathan.storyapp.ui.widget
//
//import android.content.Context
//import android.content.Intent
//import android.database.Cursor
//import android.graphics.Bitmap
//import android.widget.RemoteViews
//import android.widget.RemoteViewsService
//import androidx.core.os.bundleOf
//import com.fathan.storyapp.R
//import com.fathan.storyapp.data.responses.ListStoryItem
//
//
//internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory{
//
//    private val mWidgetItems = ArrayList<Bitmap>()
//    private lateinit var cursor:Cursor
//    private lateinit var storyList: ArrayList<ListStoryItem>
//
//    override fun onCreate() {
//        if (cursor != null){
//            storyList.clear()
//        }
////        cursor = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)!!
//
//    }
//
//    override fun onDataSetChanged() {
//        //Ini berfungsi untuk melakukan refresh saat terjadi perubahan.
//
////        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.darth_vader))
////        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.star_wars_logo))
////        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.storm_trooper))
////        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.starwars))
////        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.falcon))
//    }
//
//    override fun onDestroy() {
//
//    }
//
//    override fun getCount(): Int = mWidgetItems.size
//
//    override fun getViewAt(position: Int): RemoteViews {
//        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
//
//        val extras = bundleOf(
//            StoryWidget.EXTRA_ITEM to position
//        )
//        val fillInIntent = Intent()
//        fillInIntent.putExtras(extras)
//
//        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
//        return rv
//    }
//
//    override fun getLoadingView(): RemoteViews? = null
//
//    override fun getViewTypeCount(): Int = 1
//
//    override fun getItemId(i: Int): Long = 0
//
//    override fun hasStableIds(): Boolean = false
//
//}