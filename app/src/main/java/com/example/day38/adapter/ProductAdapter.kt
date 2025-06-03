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
        private var currentProduct:Product ?= null


        init {
            itemview.setOnClickListener {
                currentProduct?.let {
                    onClick(it,"detail")
                }
            }

            addQtyBtn.setOnClickListener {
                currentProduct?.let {

                   onClick(it,"addqty").apply {
                       firstqty = firstqty?.plus(1)!!
                   }

                }
            }


            minQtyBtn.setOnClickListener {
                currentProduct?.let {

                    onClick(it,"editqty").apply {
                        firstqty = firstqty?.minus(1)!!
                    }

                }
            }

            cartBtn.setOnClickListener {
                currentProduct?.let {
                    onClick(it,"cartBtn")
                }
            }

        }

        fun bind(product: Product) {
            currentProduct = product

            title.text = product.title
            price.text = product.price.toString()
            rating.text = product.rating.toString()
            qtyProduct.setText( firstqty.toString())


            Glide.with(itemView).load("").centerCrop().into(thumbnail)

        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product,parent,false)

        return ProductViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem((position))

        holder.bind(product)
    }

    object ProductCallback : DiffUtil.ItemCallback<Product> (){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }
}