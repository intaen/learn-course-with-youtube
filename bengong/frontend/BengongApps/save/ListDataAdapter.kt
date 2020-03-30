package com.intanmarsjaf.bengongapps.utils.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.model.Data

class ListDataAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0, var dataList: MutableList<Data>)
    : ArrayAdapter<Data>(context, layoutRes, dataList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)

        val data = dataList.get(position)
        itemView.findViewById<TextView>(R.id.user_course).setText(data.id_course)
        return itemView
    }
}