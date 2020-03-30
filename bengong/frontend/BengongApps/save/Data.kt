package com.intanmarsjaf.bengongapps.model

import java.io.Serializable

class Data(
    var id: Int,
    var id_user: Int,
    var id_course: Int
) : Serializable {

}

class ResponseData(
    var success: Boolean,
    var message: String,
    var data: List<Data>?
)

class ResponseDataDetail(
    var success: Boolean,
    var message: String,
    var data: List<DataDetail>?
)

class DataDetail(
    var id: Int,
    var id_user: Int,
    var id_course: Int
) {

}
