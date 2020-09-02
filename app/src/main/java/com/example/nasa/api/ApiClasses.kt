package com.example.nasa.api

import android.provider.ContactsContract

class ApodApi(val media_type: String?,val url: String?)

class SearchApi(val collection: Collect?)

class Collect(val items: List<Items>?)

class Items(val data: List<Content>?, val href: String?)

class Content(val nasa_id: String?, val title: String?)
