package com.intanmarsjaf.bengongapps.adapter

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
import com.squareup.picasso.Picasso

class ListCourseAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0, var courseList: MutableList<Course>)
    : ArrayAdapter<Course>(context, layoutRes, courseList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false)

        val course = courseList.get(position)
        itemView.findViewById<TextView>(R.id.lang).setText(course.lang)
        val img = itemView?.findViewById<ImageView>(R.id.lang_img)
        Picasso.get().load(course.lang_img).placeholder(R.drawable.logo).into(img)
        return itemView
    }
}