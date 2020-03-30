package com.intanmarsjaf.bengongapps.model

import java.io.Serializable

class Topic(
    var id: Int,
    var id_course: Int,
    var topic_title: String,
    var topic_link: String,
    var topic_desc: String
): Serializable {

}

class ResponseTopic(
    var success: Boolean,
    var message: String,
    var data: List<Topic>?
)

class ResponseTopicDetail(
    var success: Boolean,
    var message: String,
    var data: List<TopicDetail>?
)

class TopicDetail(
    var id: Int,
    var id_course: Int,
    var topic_title: String,
    var topic_link: String,
    var topic_desc: String
){

}
