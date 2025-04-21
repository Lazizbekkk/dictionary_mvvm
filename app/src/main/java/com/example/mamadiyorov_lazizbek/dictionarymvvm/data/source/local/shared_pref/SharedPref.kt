package com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.shared_pref

import android.content.SharedPreferences

object SharedPref {
    private lateinit var sharedPreferences: SharedPreferences

    fun setSharedPref(sharedPreferences: SharedPreferences){
        this.sharedPreferences = sharedPreferences
    }

    fun getSharedPref() = sharedPreferences

}