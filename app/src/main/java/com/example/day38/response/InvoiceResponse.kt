package com.example.day38.response

data class InvoiceResponse(
    val date:String ?=null,
    val products: List<ProductInvoice>
)

data class ProductInvoice(
    val id : Int ?=null,
    val name : String ?=null,
    val unit_price : Int ?=null,
    val quantity : Int ?=null,
    val total : Int ?=null,
)
