package com.example.nasa.search

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa.R
import com.example.nasa.api.SearchApiCall
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    val sAdapter = SearchAdapter(mutableListOf())
    var searchEmpty = true
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading.visibility = View.INVISIBLE

        searchListView.apply {
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            // set the custom adapter to the RecyclerView
            adapter = sAdapter
        }
        requireActivity().SearchFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if(loading != null){
                        if (newText != null) {
                            loading.visibility = View.VISIBLE
                            searchEmpty = false
                            sAdapter.deleteAll()
                            SearchApiCall(newText){result ->
                                if (result.collection != null){
                                    if(!result.collection.items.isNullOrEmpty()){
                                        for(i in result.collection.items){
                                            if(i.data != null) {
                                                if(!i.data[0].title.isNullOrEmpty()){
                                                    activity?.runOnUiThread {
                                                        if(!searchEmpty){
                                                            sAdapter.addData(i.data[0].title, i.data[0].nasa_id)
                                                        }
                                                        loading.visibility = View.INVISIBLE
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        activity?.runOnUiThread {
                                            Toast.makeText(requireContext(),"No results found!",Toast.LENGTH_SHORT).show()
                                            loading.visibility = View.INVISIBLE
                                        }
                                    }
                                }

                            }
                        }
                        if(newText.isNullOrEmpty()){
                            loading.visibility = View.INVISIBLE
                            sAdapter.deleteAll()
                            searchEmpty = true
                        }
                    }
                },500)
                return true
            }
        })

    }

}