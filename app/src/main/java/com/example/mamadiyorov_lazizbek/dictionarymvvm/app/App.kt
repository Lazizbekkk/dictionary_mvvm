package com.example.mamadiyorov_lazizbek.dictionarymvvm.app

import android.app.Application
import android.view.Display.Mode
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.database.MyDatabase
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.shared_pref.SharedPref
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.setSharedPref(getSharedPreferences("dictionary", MODE_PRIVATE))
        MyDatabase.init(this)
        val database = MyDatabase.database
        AppRepositoryImpl.init(database.dictionaryDao())
    }
}