package com.example.day38

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.day38.adapter.ProductAdapter
import com.example.day38.api.ApiClient
import com.example.day38.response.Product
import com.example.day38.response.ProductResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment(),ProductAdapter.onPaidListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var qtyProduct : TextView
    private lateinit var totalBayarTxt : TextView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var bayarSekarang: Button
    private  var qty:Int?=0;
    private var totalBayar : Int ?= 0
    private lateinit var arrayList: ArrayList<InvoiceClass>

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        bayarSekarang = view.findViewById(R.id.bayar_sekarang)
        recyclerView = view.findViewById(R.id.recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout)
        searchView = view.findViewById(R.id.search_view)
        qtyProduct = view.findViewById(R.id.qtyProduct)
        totalBayarTxt = view.findViewById<TextView>(R.id.txt_totalBayar)
        productAdapter = ProductAdapter{product, s -> run {
            when(s) {
                "detail" -> productOnclick()
                "delete" -> deleteProduct(product)
                "addqty" -> addQtyProduct(product)
                "editqty" -> editQtyProduct(product)
                "cartBtn" -> cartBtnOnclick(product)
            }
        }
        }

        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        swipeRefreshLayout.setOnRefreshListener {
            products()
        }

        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchProducts(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchProducts(newText.toString())
                return false
            }

        })

        bayarSekarang.setOnClickListener {
            val intent = Intent(context,InvoiceActivity::class.java)
            val sph = context?.getSharedPreferences("bayar",Context.MODE_PRIVATE)
            startActivity((intent))
        }

        products()

        return  view
    }

    fun products() {
        swipeRefreshLayout.isRefreshing = true
        ApiClient.getApiService.products()
            .enqueue(object : Callback<ProductResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    swipeRefreshLayout.isRefreshing = false

                    val products = response.body()?.products
                    println("products " + products)

                    if(response.isSuccessful) {
                        productAdapter.submitList(products)
                        productAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(context,t.localizedMessage,Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun searchProducts(query : String) {
        swipeRefreshLayout.isRefreshing = true
        ApiClient.getApiService.searchProducts(query)
            .enqueue(object : Callback<ProductResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    swipeRefreshLayout.isRefreshing = false
                    val products = response.body()?.products
                    println("search  " + products)

                    if(response.isSuccessful) {
                        productAdapter.submitList(products)
                        productAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(context,t.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            })


}

    fun cartBtnOnclick(product: Product) {
        val sph = context?.getSharedPreferences("bayar",Context.MODE_PRIVATE)
        val edit = sph?.edit()
        val gson = Gson()

        product.price?.toInt()
            ?.let { product.category?.let { it1 -> product.title?.let { it2 -> InvoiceClass(it2, it1, it) } } }?.let { arrayList.add(it) }

        val json = gson.toJson(arrayList)

        edit?.putString("orders_data",json)?.apply()


    }



    fun productOnclick() {
    }

    @SuppressLint("SetTextI18n")
    fun editQtyProduct(product: Product) {
        val totalHarga = product.price?.toInt()?.times(15000)

        println("price" + totalHarga)

        totalBayar = totalHarga?.let { totalBayar ?.minus(it) }

        totalBayarTxt.text = "Rp. " + totalBayar.toString()
    }

    @SuppressLint("SetTextI18n")
    fun addQtyProduct(product: Product) {
        val totalHarga = product.price?.toInt()?.times(15000)

        println("price" + totalHarga)

        totalBayar = totalHarga?.let { totalBayar ?.plus(it) }

        totalBayarTxt.text = "Rp. " + totalBayar.toString()
    }

    fun deleteProduct(product: Product) {
        val id = product.id
        if (id != null) {
            ApiClient.getApiService.deleteProduct(id)
                .enqueue(object :Callback<Product> {
                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        val product = response.body()
                        println("delete product  " + product)

                        if(product != null) {
                            Toast.makeText(context,"delete berhasil",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        Toast.makeText(context,t.localizedMessage,Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    override fun onProductPriceIncreased(price: Int) {
        val product = Product()
        addQtyProduct(product)
    }

    override fun onProductPriceDecreased(price: Int) {
        val product = Product()
        editQtyProduct(product)
    }
}