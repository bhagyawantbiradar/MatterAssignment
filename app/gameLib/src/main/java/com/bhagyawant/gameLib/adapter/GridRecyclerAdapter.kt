package com.bhagyawant.gameLib.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bhagyawant.gameLib.R

class GridRecyclerAdapter(val context : Context, private val itemList : Array<IntArray>) :
    RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvItem : TextView = itemView.findViewById(R.id.tv_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position/itemList[0].size
        val col = position % itemList[0].size
        val item = itemList[row][col]
        if(item!=0){
            holder.tvItem.text = "$item"
            when(item){
                2 ->{
                 holder.itemView.setBackgroundColor(context.getColor(R.color.for_2))
                }
                4 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_4))
                }
                8 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_8))
                }
                16 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_16))
                }
                32 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_32))
                }
                64 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_64))
                }
                128 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_128))
                }
                256 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_256))
                }
                512 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_512))
                }
                1024 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_1024))
                }
                2048 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_2048))
                }
                4096 ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_4096))
                }
                else ->{
                    holder.itemView.setBackgroundColor(context.getColor(R.color.for_else))
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return itemList.size * itemList[0].size
    }
}