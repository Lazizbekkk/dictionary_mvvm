package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.favoriet

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity

interface FavorietsViewModel {
    val openWordDetailScreen: LiveData<DictionaryEntity>
    val favoritesCursorLiveData: LiveData<Cursor>
    val itemClicked: LiveData<Int>
    val updateTypeLiveData: LiveData<String>
    val setVisibilityLiveData: LiveData<Boolean>


    fun updateFav(id: Int, isFav: Int)
    fun itemClicked(idInt: Int)
    fun getAllCursorFavorites(): Cursor
    fun openWordDetailScreen(dictionaryEntity: DictionaryEntity)
    fun updateType()
}