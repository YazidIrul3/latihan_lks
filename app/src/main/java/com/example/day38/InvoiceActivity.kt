package com.example.day38

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class InvoiceActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invoice)

        val txtDate = findViewById<TextView>(R.id.txt_date)
        val txt_totalBayar = findViewById<TextView>(R.id.txt_totalBayar)
        val tableProducts = findViewById<TableLayout>(R.id.table_product)

        val invoiceString = intent.getStringExtra("invoice")
        val invoiceJson = JSONObject(invoiceString)

        val rowDate = invoiceJson.getString("date")
        val formattedDate = formatDate(rowDate)
        txtDate.text = "Tanggal: $formattedDate"

        val productsArray : JSONArray = invoiceJson.getJSONArray("products")
        for (i in 0 until productsArray.length()) {
            val item = productsArray.getJSONObject(i)
            val title = item.getString("name")
            val price = item.getInt("price")
            println("item_invoice" + item)
            println("invoice_title" + title)
            println("invoice_price" + price)

            val row = TableRow(this)
            row.addView(createCell(title,50))
            tableProducts.addView(row)

            val totalPayment = invoiceJson.getInt("total_payment")
            println("total_payment " + totalPayment)
        }

        println("semua_invoice" + productsArray)
        println("semua_invoicejson" + invoiceJson)
    }

    private fun formatDate(date:String) : String? {
        val sdInput = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        val sdOutput = SimpleDateFormat("yyyy-MM-dd",Locale("id","ID"))
        val parsedDate = sdInput.parse(date)

        return parsedDate?.let { sdOutput.format(it) }
    }

    fun createCell(text : String,maxLength: Int = 20) : TextView? {
        val trimmedText = if (text.length > maxLength) text.substring(0, maxLength) + "..." else text
        return TextView(this)?.apply {
            setPadding(8,8,8,8)
            setText(trimmedText)
        }
    }

    fun formatCurrency (amount: Int): String {
        return String.format("%,of",amount * 1000)
    }
}