package com.example.mamadiyorov_lazizbek.dictionarymvvm.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity

interface AppRepository {
    fun getCursor(): Cursor

    fun changeFavById(id: Int, fav: Int)

    fun getWordById(id: Int): LiveData<DictionaryEntity>

    fun update(dictionaryEntity: DictionaryEntity)

    fun searchWord(query: String): Cursor

    fun getCursorByFavorites(): Cursor

    fun setType(type: String)
    fun getType(): String


}