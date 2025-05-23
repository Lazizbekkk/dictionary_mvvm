package com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dictionary")
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val uzbek: String?,
    val english:String?,
    val type: String?,
    @ColumnInfo("is_favourite")
    var isFavourite: Int?,
    val countable: String?,
    val transcript: String?,
): Serializable

