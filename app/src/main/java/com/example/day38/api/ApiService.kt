package com.example.day38.api

import com.example.day38.request.LoginRequest
import com.example.day38.request.ProductRequest
import com.example.day38.response.LoginResponse
import com.example.day38.response.Product
import com.example.day38.response.ProductResponse
import com.example.day38.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type:application/json")
    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @GET("auth/me")
    fun profile(
        @Header("Authorization") token : String
    ) : Call<ProfileResponse>

    @GET("products")
    fun products() :Call<ProductResponse>

    @GET("products/search")
    fun searchProducts(
        @Query("q") query : String
    ) :Call<ProductResponse>

    @Headers("Content-Type:application/json")
    @POST("products/add")
    fun addProduct(
        @Body productRequest: ProductRequest
    ) : Call<Product>

    @Headers("Content-Type:application/json")
    @PUT("products/{id}")
    fun editProduct(
        @Body productRequest: ProductRequest,
        @Path("id") id : Int,
    ) : Call<Product>


    @DELETE("products/{id}")
    fun deleteProduct(
        @Path("id") id : Int
    ) :Call<Product>
}