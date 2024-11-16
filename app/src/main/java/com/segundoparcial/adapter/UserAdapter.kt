package com.example.myapplication.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.segundoparcial.R
import com.segundoparcial.model.User

class UserAdapter(private val userList : List<User>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  ViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("User" , userList.toString())
        holder.render(userList[position])
    }

    override fun getItemCount(): Int  = userList.size

}

class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.findViewById<TextView>(R.id.tvItemName)
    val tvItemDescription = view.findViewById<TextView>(R.id.tvItemDescription)
    val ivItemImg = view.findViewById<ImageView>(R.id.ivItemFoto)
    val cgType = view.findViewById<ChipGroup>(R.id.cgType)

    fun render(user : User){
        tvItemName.text = user.name
        tvItemDescription.text  =user.description
        val list = user.type.split(",")
        generateChip(list)
        ivItemImg.setImageURI(Uri.parse(user.img))
    }

    private fun generateChip(list:List<String>) {
        list.forEach { type ->
            if (cgType.children.none { (it as? Chip)?.text == type }) {
                val chip = Chip(cgType.context)
                chip.text = type
                cgType.addView(chip)
            }
        }
    }
}

