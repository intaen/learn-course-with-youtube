package com.intanmarsjaf.bengongapps.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Course(
    var id: Int,
    var lang: String,
    var lang_img: String
): Serializable {

}

class ResponseCourse(
    var success: Boolean,
    var message: String,
    var data: List<Course>?
)

class ResponseCourseDetail(
    var success: Boolean,
    var message: String,
    var data: List<CourseDetail>?
)

class CourseDetail(
    var id: Int,
    var id_course: Int,
    var topic_title: String,
    var topic_link: String
){

}
