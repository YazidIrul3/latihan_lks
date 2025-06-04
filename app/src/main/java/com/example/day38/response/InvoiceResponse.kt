package com.example.day38.response

data class InvoiceResponse(
    val date:String ?=null,
    val products: List<ProductInvoice>
)

data class ProductInvoice(
    val id : Int ?=null,
    val title : String ?=null,
    val price : Float ?=null,
    val qty : Int ?=null,
    val total : Int ?=null,
)
