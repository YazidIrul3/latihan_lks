package com.example.day38

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.day38.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nav)

        findViewById<BottomNavigationView>(R.id.nav_view).setOnItemSelectedListener { item ->
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
        secondFragment.replace(R.id.fragment_layout,fragment).commit()
    }
}