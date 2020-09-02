package com.example.nasa.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa.R
import kotlinx.android.synthetic.main.search_list.view.*

class SearchAdapter(private var list: MutableList<SearchElement>)
    : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_list, parent, false)
        return SearchViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val element = list[position]
        holder.view.title.text = element.title
        holder.view.setOnClickListener {
            val intent = Intent(holder.view.context, SearchResult::class.java)
            intent.putExtra("title",element.title)
            intent.putExtra("id",element.id)
            holder.view.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = list.size

    fun addData(title: String?, id: String?){
        if (title != null && id != null) {
            list.add(SearchElement(title, id))
        }
        notifyDataSetChanged()

    }

    fun deleteAll(){
        list.clear()
        notifyDataSetChanged()
    }
}


class SearchViewHolder(val view: View) :
    RecyclerView.ViewHolder(view)