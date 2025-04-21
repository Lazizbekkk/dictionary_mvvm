package com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity

@Dao
interface DictionaryDao : BaseDao<DictionaryEntity> {


    @Query("""
    SELECT * 
    FROM dictionary 
    WHERE uzbek LIKE :st || '%' 
    ORDER BY 
        CASE 
            WHEN uzbek GLOB '[A-Z]*' THEN substr(uzbek, 1, 1) || 'a'
            WHEN uzbek GLOB '[a-z]*' THEN upper(substr(uzbek, 1, 1)) || 'b'
        END,
        uzbek
""")
    fun getSearchWordsUz(st: String): Cursor

    @Query("""
    SELECT * 
    FROM dictionary 
    WHERE english LIKE :st || '%' 
    ORDER BY 
        CASE 
            WHEN english GLOB '[A-Z]*' THEN substr(english, 1, 1) || 'a'
            WHEN english GLOB '[a-z]*' THEN upper(substr(english, 1, 1)) || 'b'
        END,
        english
""")
    fun getSearchWordsEng(st: String): Cursor

//    @Query("select * from dictionary where dictionary.english like :st || '%'   ORDER BY uzbek ")
//    fun getSearchWordsEng(st: String): Cursor

    @Query("select * from dictionary where dictionary.english like :st || '%'  limit :limit")
    fun getSearchWordsEnLimit(st: String, limit: Int): Cursor


    @Query("select * from dictionary")
    fun getAllWords(): List<DictionaryEntity>

    @Query("SELECT * FROM dictionary")
    fun getWordsCursor(): Cursor

    @Query("""
    SELECT * 
    FROM dictionary 
    where is_favourite = 1
    ORDER BY 
        CASE 
            WHEN uzbek GLOB '[A-Z]*' THEN substr(uzbek, 1, 1) || 'a'
            WHEN uzbek GLOB '[a-z]*' THEN upper(substr(uzbek, 1, 1)) || 'b'
        END,
        uzbek
""")
    fun getFavoriteWordsUz(): Cursor

    @Query("""
    SELECT * 
    FROM dictionary 
    where is_favourite = 1
    ORDER BY 
        CASE 
            WHEN english GLOB '[A-Z]*' THEN substr(english, 1, 1) || 'a'
            WHEN english GLOB '[a-z]*' THEN upper(substr(english, 1, 1)) || 'b'
        END,
        english
""")
//    @Query("select * from dictionary where is_favourite = 1")
    fun getFavoriteWordsEng(): Cursor



    @Query("UPDATE  dictionary SET is_favourite = :fav WHERE id = :id")
    fun updateFav(id: Int, fav: Int)

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWordById(id: Int): LiveData<DictionaryEntity>

//    @Query("""
//    SELECT *
//    FROM dictionary
//    ORDER BY
//        CASE
//            WHEN uzbek GLOB '[A-Z]*' THEN lower(uzbek) || 'a'
//            WHEN uzbek GLOB '[a-z]*' THEN lower(uzbek) || 'b'
//        END
//""")

    @Query("""
    SELECT * 
    FROM dictionary
    ORDER BY 
        CASE 
            WHEN uzbek GLOB '[A-Z]*' THEN substr(uzbek, 1, 1) || 'a'
            WHEN uzbek GLOB '[a-z]*' THEN upper(substr(uzbek, 1, 1)) || 'b'
        END,
        uzbek
""")
    fun getAllUzb(): Cursor






    @Query("""
    SELECT * 
    FROM dictionary
    ORDER BY 
        CASE 
            WHEN english GLOB '[A-Z]*' THEN substr(english, 1, 1) || 'a'
            WHEN english GLOB '[a-z]*' THEN upper(substr(english, 1, 1)) || 'b'
        END,
        english
""")
    fun getAllEng(): Cursor



}