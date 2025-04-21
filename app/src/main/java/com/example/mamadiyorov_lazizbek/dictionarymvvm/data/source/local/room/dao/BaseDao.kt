package com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T)

}