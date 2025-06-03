package com.example.day38.response

data class ProductResponse(
    val products : List<Product>
)

data class Product(
    val id:Int ?=null,
    val title:String ?=null,
    val description:String ?=null,
    val thumbnail:String ?=null,
    val category:String ?=null,
    val price:Float ?=null,
    val rating:Float ?=null,
    val stock:Int ?=null,
)
