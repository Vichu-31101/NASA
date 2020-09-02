package com.example.nasa.apod

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nasa.R
import com.example.nasa.api.ApodApiCall
import kotlinx.android.synthetic.main.fragment_apod.*
import kotlinx.android.synthetic.main.fragment_apod.view.*


class ApodFragment : Fragment() {
    var isDisplay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apod, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datePicker.visibility = View.VISIBLE
        loading.visibility = View.INVISIBLE
        image.visibility = View.INVISIBLE
        video.visibility = View.INVISIBLE
        datePicker.maxDate = System.currentTimeMillis()
        apodSearch.setOnClickListener {
            if(!isDisplay){
                val year = datePicker.year
                val month = datePicker.month+1
                val day = datePicker.dayOfMonth
                val date = "$year-$month-$day"
                datePicker.visibility = View.INVISIBLE
                loading.visibility = View.VISIBLE
                apodSearch.text = date
                ApodApiCall(date){result ->
                    if(!result.media_type.isNullOrEmpty())
                    {
                        if(result.media_type == "image"){
                            if (!result.url.isNullOrEmpty()) {
                                Log.d("hey",result.url)

                                Glide.with(this)
                                    .asBitmap()
                                    .load(result.url)
                                    .into(object : CustomTarget<Bitmap>(){
                                        override fun onLoadCleared(placeholder: Drawable?) {
                                        }

                                        override fun onResourceReady(
                                            resource: Bitmap,
                                            transition: Transition<in Bitmap>?
                                        ) {
                                        requireActivity().runOnUiThread {
                                            if(datePicker.visibility == View.INVISIBLE){
                                                image.setImageBitmap(resource)
                                                loading.visibility = View.INVISIBLE
                                                image.visibility = View.VISIBLE
                                            }
                                        }
                                        }
                                    })

                            }

                        }
                        else{
                            if(!result.url.isNullOrEmpty()){
                                requireActivity().runOnUiThread {
                                    if(datePicker.visibility == View.INVISIBLE){
                                        video.settings.javaScriptEnabled = true
                                        video.settings.pluginState = WebSettings.PluginState.ON
                                        video.loadUrl(result.url)
                                        video.webViewClient = object : WebViewClient() {
                                            override fun onPageFinished(
                                                view: WebView,
                                                url: String
                                            ) {
                                                loading.visibility = View.INVISIBLE
                                                video.visibility = View.VISIBLE
                                            }
                                        }
                                        video.webChromeClient = WebChromeClient()

                                    }
                                }
                            }
                        }
                    }
                    else{
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(),"Error in fetching result",Toast.LENGTH_SHORT).show()
                            apodSearch.text = "APOD"

                            datePicker.visibility = View.VISIBLE
                            loading.visibility = View.INVISIBLE
                            image.visibility = View.INVISIBLE
                            video.visibility = View.INVISIBLE
                            isDisplay = false
                        }
                    }
                }
                isDisplay = true
            }
            else{
                apodSearch.text = "APOD"
                datePicker.visibility = View.VISIBLE
                loading.visibility = View.INVISIBLE
                image.visibility = View.INVISIBLE
                video.loadUrl("")
                video.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(
                        view: WebView,
                        url: String
                    ) {
                        video.visibility = View.INVISIBLE
                    }
                }
                isDisplay = false
            }


        }
    }

}