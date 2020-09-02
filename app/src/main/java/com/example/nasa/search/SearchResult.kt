package com.example.nasa.search

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nasa.R
import com.example.nasa.api.SearchImageApiCall
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.activity_search_result.loading
import kotlinx.android.synthetic.main.fragment_apod.*

class SearchResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        var title = intent.getStringExtra("title")
        var id = intent.getStringExtra("id")
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loading.visibility = View.VISIBLE
        resultImage.visibility = View.INVISIBLE
        SearchImageApiCall(id){ result ->
            if(result.collection != null){
                if(!result.collection.items.isNullOrEmpty())
                {
                    if(!result.collection.items[0].href.isNullOrEmpty()){
                        var url = result.collection.items[0].href.toString()
                        url = url.substring(0,4)+"s"+url.substring(4,url.length)
                        Log.d("test123",url)
                        Glide.with(this)
                            .asBitmap()
                            .load(url)
                            .into(object : CustomTarget<Bitmap>(){
                                override fun onLoadCleared(placeholder: Drawable?) {
                                }

                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    runOnUiThread {
                                        resultImage.setImageBitmap(resource)
                                        loading.visibility = View.INVISIBLE
                                        resultImage.visibility = View.VISIBLE
                                    }
                                }
                            })
                    }
                }
            }

        }






    }
    override fun onOptionsItemSelected(item:android.view.MenuItem):Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onClickHome()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun onClickHome() {
        super.onBackPressed()
    }
}