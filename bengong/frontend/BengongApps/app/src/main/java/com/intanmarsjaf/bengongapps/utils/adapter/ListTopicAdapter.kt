package com.intanmarsjaf.bengongapps.utils.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.Topic
import com.squareup.picasso.Picasso

class ListTopicAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0, var topicList: MutableList<Topic>)
    : ArrayAdapter<Topic>(context, layoutRes, topicList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false)

        val topic = topicList.get(position)
        itemView.findViewById<TextView>(R.id.topic).setText(topic.topic_title)
        return itemView
    }
}