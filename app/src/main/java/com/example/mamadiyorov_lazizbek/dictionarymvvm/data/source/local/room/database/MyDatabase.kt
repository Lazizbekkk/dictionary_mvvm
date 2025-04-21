package com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.dao.DictionaryDao

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao


    companion object {
        lateinit var database: MyDatabase
            private set

        fun init(context: Context) {
            if (!Companion::database.isInitialized) database =
                Room.databaseBuilder(context, MyDatabase::class.java, "MyDatabase.db")
                    .createFromAsset("dictionary.db").allowMainThreadQueries().build()
        }
    }
}