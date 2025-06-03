package com.example.day38.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.day38.R
import com.example.day38.response.ProductInvoice

class InvoiceAdapter (private val onClick:(ProductInvoice,String) -> Unit) :
        ListAdapter<ProductInvoice,InvoiceAdapter.InvoiceViewHolder>(InvoiceCallback) {
    class   InvoiceViewHolder(itemview : View, val onClick: (ProductInvoice, String) -> Unit)
        :RecyclerView.ViewHolder(itemview){

            val title_invoice = itemview.findViewById<TextView>(R.id.title_invoice)

        var currentInvoice : ProductInvoice ?=null

        init {
            itemview.setOnClickListener {
                currentInvoice?.let {
                    onClick(it,"detail")
                }
            }
        }

        fun bind(productInvoice: ProductInvoice) {
            currentInvoice = productInvoice

            title_invoice.text = productInvoice.name
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice,parent,false)

        return InvoiceViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val productInvoice = getItem(position)

        holder.bind(productInvoice)
    }

    object InvoiceCallback : DiffUtil.ItemCallback<ProductInvoice>() {
        override fun areItemsTheSame(oldItem: ProductInvoice, newItem: ProductInvoice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductInvoice, newItem: ProductInvoice): Boolean {
            return oldItem.id == newItem.id

        }

    }
}