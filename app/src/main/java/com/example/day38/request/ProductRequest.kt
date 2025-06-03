package com.example.day38.request

data class ProductRequest(
    val title:String ?=null,
    val brand:String ?=null,
    val price:Float ?=null,
)
