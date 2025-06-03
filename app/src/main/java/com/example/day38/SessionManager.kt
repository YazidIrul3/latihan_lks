package com.example.day38

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context : Context) {
    var sharedPreferences : SharedPreferences ?= null
    var editor : SharedPreferences.Editor ?= null

    init {
        sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    fun setToken(token : String) {
        editor?.putString("token",token)?.apply()
    }

    fun getToken() :String ?{
        return sharedPreferences?.getString("token",null)
    }
}