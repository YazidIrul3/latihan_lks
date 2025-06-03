package com.example.day38

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.product -> {
                    replaceFragment(ProductFragment())
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
            return@setOnItemSelectedListener false
        }

        replaceFragment(ProductFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        val secondFragment = supportFragmentManager.beginTransaction()
        secondFragment.replace(R.id.frame_layout,fragment).commit()
    }
}