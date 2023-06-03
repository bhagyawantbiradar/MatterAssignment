package com.bhagyawant.gameLib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyawant.gameLib.R

class GridRecyclerAdapter(private val itemList : Array<IntArray>) :
    RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvItem : TextView = itemView.findViewById(R.id.tv_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position/itemList[0].size
        val col = position % itemList[0].size
        val item = itemList[row][col]
        holder.tvItem.text = "$item"

    }

    override fun getItemCount(): Int {
        return itemList.size * itemList[0].size
    }
}