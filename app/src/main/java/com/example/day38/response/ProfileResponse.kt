package com.example.day38.response

data class ProfileResponse(
    val username:String ?=null,
    val email:String ?=null,
    val firstName:String ?=null,
    val lastName:String ?=null,
    val gender:String ?=null,
    val image:String ?=null,
)
