package com.example.githubuser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.R

class UserAdapter(private val context: Context, private val listUser: ArrayList<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (avatarUrl, id, login) = listUser[position]
        viewHolder.tvItem.text = login
        Glide.with(context)
            .load(avatarUrl)
            .into(viewHolder.imgView)

        viewHolder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])}
    }
    override fun getItemCount() = listUser.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.nameTextView)
        val imgView: ImageView = view.findViewById(R.id.photoImageView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data : ItemsItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
