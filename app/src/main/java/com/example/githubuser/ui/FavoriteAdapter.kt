package com.example.githubuser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.FavoriteUser

class FavoriteAdapter(private val context: Context, private val listUser: ArrayList<FavoriteUser>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (id, username, avatar) = listUser[position]
        viewHolder.tvItem.text = username
        Glide.with(context)
            .load(avatar)
            .into(viewHolder.imgView)

        viewHolder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])}
    }
    override fun getItemCount() = listUser.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.nameTextView)
        val imgView: ImageView = view.findViewById(R.id.photoImageView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
