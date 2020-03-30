package com.intanmarsjaf.bengongapps.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.adapter.ListCourseAdapter
import com.intanmarsjaf.bengongapps.api.CourseAPI
import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.retrofit_client.RetrofitClient
import com.intanmarsjaf.bengongapps.storage.sharedPreferences
import com.intanmarsjaf.bengongapps.view_model.CourseViewModel
import kotlinx.android.synthetic.main.activity_home.*
import java.io.Serializable

class HomeActivity : AppCompatActivity() {

    private lateinit var getSharedPreferences: sharedPreferences
    private lateinit var context: Context
    lateinit var getData: String

    lateinit var btnLogout: Button
    lateinit var profile: TextView

    lateinit var listView: ListView
    lateinit var arrayAdapter: ListCourseAdapter

    var listCourse = mutableListOf<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnLogout=findViewById(R.id.logout)
        profile=findViewById(R.id.profile_email)

        getSharedPreferences = sharedPreferences(this)
        context=this

        getData = getSharedPreferences.getString(getSharedPreferences._email).toString()
        findViewById<TextView>(R.id.profile_email).setText(getData).toString()

        listCourse()

        btnLogout.setOnClickListener{
            this?.let {
                getSharedPreferences.clear()
                startActivity(
                    Intent(it, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun listCourse() {
        val courseAPI = RetrofitClient.createRetrofit().create(CourseAPI::class.java)
        val courseViewModel = ViewModelProviders.of(this).get(CourseViewModel::class.java)
        courseViewModel?.courseAPI = courseAPI

        courseViewModel.courses?.observe(this, Observer { course ->
            if (course == null) {
                print("Error")
            } else {
                getAllCourse(course)
            }
        })

        courseViewModel?.getAllCourse()

        listView = findViewById<ListView>(R.id.course_list)
        arrayAdapter = ListCourseAdapter(
            context = this,
            courseList = listCourse
        )
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            startActivity(Intent(this, TopicByCourseActivity::class.java).apply {
                putExtra("id", listCourse[position].id.toString())
                putExtra("topic", listCourse[position].lang)
            })
        }
    }

    fun getAllCourse(course: List<Course>){
        arrayAdapter.clear()
        for (i in 0 until course.size){
            println("fetchGetAllCourse : "+course[i].lang)
            arrayAdapter.add(course[i])
        }
    }
}
