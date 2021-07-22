package com.depromeet.threedollar.admin.common.dto

data class ApiResponse<T>(
    val resultCode: String,
    val message: String,
    val data: T

) {

    companion object {
        fun <T> success(data: T) = ApiResponse("", "", data)
        val OK = success("OK")
    }

}
