package com.example.youtubetest.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubetest.R
import com.example.youtubetest.models.Video

class VideoAdapter(private val videoList: ArrayList<Video>) :
    RecyclerView.Adapter<VideoAdapter.MyViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.video_list_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = videoList[position]
        holder.videoTitle.text = currentItem.videoTitle
        holder.viewImage.setImageDrawable(currentItem.videoImage.drawable)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val videoTitle: TextView = itemView.findViewById(R.id.video_title)
        val viewImage: ImageView = itemView.findViewById(R.id.video_image)
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}