package com.intanmarsjaf.bengongapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.api.TopicAPI
import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.Topic
import com.intanmarsjaf.bengongapps.retrofit_client.RetrofitClient
import com.intanmarsjaf.bengongapps.utils.adapter.ListTopicAdapter
import com.intanmarsjaf.bengongapps.view_model.TopicViewModel
import kotlinx.android.synthetic.main.activity_topic_detail.view.*
import kotlinx.android.synthetic.main.item_topic.*
import java.io.Serializable

class TopicByCourseActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var arrayAdapter: ListTopicAdapter
    var listTopic = mutableListOf<Topic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_by_course)
        val courseID = intent.getStringExtra("id")
        val courseName = intent.getStringExtra("topic")
        findViewById<TextView>(R.id.profile_topic).setText(courseName).toString()

        val topicAPI = RetrofitClient.createRetrofit().create(TopicAPI::class.java)
        val topicViewModel = ViewModelProviders.of(this).get(TopicViewModel::class.java)
        topicViewModel?.topicAPI = topicAPI
        topicViewModel?.getTopicByCourse(courseID!!.toInt())

        topicViewModel.topics?.observe(this, Observer {
                topic -> if(topic==null){
            print("Error")
        }else {
            getAllTopic(topic)
        }
        })

        listView = findViewById<ListView>(R.id.topic_list)
        arrayAdapter = ListTopicAdapter(
            context = this,
            topicList = listTopic)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener{ _, _, position, _ ->
            startActivity(Intent(this, TopicDetailActivity::class.java).apply {
                putExtra("id",listTopic[position].id.toString())
                putExtra("title", listTopic[position].topic_title)
                putExtra("link", listTopic[position].topic_link)
                putExtra("desc", listTopic[position].topic_desc)
            })

        }
    }

    fun getAllTopic(topic: List<Topic>){
        arrayAdapter.clear()
        for (i in 0 until topic.size){
            println("fetchGetAllTopic : "+topic[i].topic_title)
            arrayAdapter.add(topic[i])
        }
    }
}
