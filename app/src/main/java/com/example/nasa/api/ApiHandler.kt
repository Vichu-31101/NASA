package com.example.nasa.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
val client = OkHttpClient()
//Apod
fun ApodApiCall(type: String, resultHandler: (ApodApi) -> Unit){
    //println("Connecting")

    val bUrl: String = "https://api.nasa.gov/planetary/apod?api_key=ahtlz8vepLJcnWzVe01SFscSyhjmWxze4OS3FUgA&date="

    val url = bUrl + type
    Log.d("term",url)

    val request = Request.Builder().url(url).build()


    client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Failed$e")
        }

        override fun onResponse(call: Call, response: Response) {
            val body =  response.body?.string()
            println(body)

            val gson = GsonBuilder().create()

            resultHandler(gson.fromJson(body, ApodApi::class.java))
        }
    })
}

//Search
fun SearchApiCall(type: String, resultHandler: (SearchApi) -> Unit){
    println("Connecting")

    val bUrl: String = "https://images-api.nasa.gov/search?media_type=image&q="


    val url = bUrl + type
    Log.d("term",url)

    val request = Request.Builder().url(url).build()

    //val client = OkHttpClient()
    client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Failed$e")
        }

        override fun onResponse(call: Call, response: Response) {
            val body =  response.body?.string()
            println(body)

            val gson = GsonBuilder().create()

            resultHandler(gson.fromJson(body, SearchApi::class.java))
        }
    })
}

fun SearchImageApiCall(type: String, resultHandler: (SearchApi) -> Unit){
    //println("Connecting")

    val bUrl: String = "https://images-api.nasa.gov/asset/"


    val url = bUrl + type
    Log.d("term",url)

    val request = Request.Builder().url(url).build()

    //val client = OkHttpClient()
    client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("Failed$e")
        }

        override fun onResponse(call: Call, response: Response) {
            val body =  response.body?.string()
            //println(body)

            val gson = GsonBuilder().create()

            resultHandler(gson.fromJson(body, SearchApi::class.java))
        }
    })
}