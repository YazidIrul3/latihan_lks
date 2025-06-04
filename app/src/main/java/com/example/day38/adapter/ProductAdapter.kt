package com.example.day38.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.day38.R
import com.example.day38.response.Product

class ProductAdapter (private  val onClick:(Product,String) -> Unit):
        ListAdapter<Product,ProductAdapter.ProductViewHolder>(ProductCallback) {
    var currentProduct:Product ?= null
            private val quantities = mutableMapOf<Int,Int>()

    interface onPaidListener {
        fun onProductPriceIncreased(price:Int)
        fun onProductPriceDecreased(price:Int)
    }
    class ProductViewHolder(itemview : View, val onClick: (Product, String) -> Unit)
        :RecyclerView.ViewHolder(itemview) {
            val title = itemview.findViewById<TextView>(R.id.title_product)
            val price = itemview.findViewById<TextView>(R.id.price)
            val qtyProduct = itemview.findViewById<TextView>(R.id.qtyProduct)
            val rating = itemview.findViewById<TextView>(R.id.rating_product)
            val thumbnail = itemview.findViewById<ImageView>(R.id.thumbnail_product)
            val cartBtn = itemview.findViewById<ImageButton>(R.id.cart_product)
            val addQtyBtn = itemview.findViewById<ImageButton>(R.id.addQtyProduct)
            val minQtyBtn = itemview.findViewById<ImageButton>(R.id.editQtyProduct)
             var firstqty = 0
        val itemview = itemview



        init {


        }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product,parent,false)

        return ProductViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem((position))
            currentProduct = product

            holder.title.text = product.title
        holder.price.text = product.price.toString()
        holder.rating.text = product.rating.toString()
        holder.qtyProduct.setText( holder.firstqty.toString())


            Glide.with(holder.itemView).load(product.thumbnail).
            centerCrop().into(holder.thumbnail)


        holder.addQtyBtn.setOnClickListener {
            holder.firstqty= holder.qtyProduct.text.toString().toInt()?.plus(1)!!
            holder.qtyProduct.text = holder.firstqty.toString()
            quantities[position] = holder.firstqty

            currentProduct?.let {
                onClick(it,"addqty")
            }
        }


        holder.minQtyBtn.setOnClickListener {
            if(holder.qtyProduct.text.toString().toInt() >0) {

            holder.firstqty= holder.qtyProduct.text.toString().toInt()?.minus(1)!!
            holder.qtyProduct.text = holder.firstqty.toString()
                quantities[position] = holder.firstqty
                currentProduct?.let {
                    onClick(it,"editqty")
                }
        }

        }

       holder.itemview.setOnClickListener {
            currentProduct?.let {
                onClick(it,"detail")
            }
        }

        holder.cartBtn.setOnClickListener {
            currentProduct?.let {
                onClick(it,"cartBtn")
            }
        }



    }

    object ProductCallback : DiffUtil.ItemCallback<Product> (){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }

    fun getSelectedProduct() : List<Product> {
        val selected = mutableListOf<Product>()

        for ((position,qty) in quantities) {
            if(qty > 0) {
                val product = currentProduct

                if(product != null) {
                    selected.add(
                        Product(
                            title = product.title.orEmpty(),
                            price = product.price
                        )
                    )
                }
            }
        }
        return selected
    }
}