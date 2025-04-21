package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.dictionary

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity

interface DictionaryViewModel {
    val openWordDetailScreen: LiveData<DictionaryEntity>
    val dictionaryCursorLiveData: LiveData<Cursor>
    val openWordDetailsScreen: LiveData<Int>
    val updateWordType: LiveData<String>
    val setVisibilitySearchPH: LiveData<Boolean>
    val setVisibilityAlphabetRec: LiveData<Boolean>


    fun searchWord(query: String)
    fun updateFav(id: Int, isFav: Int)
    fun openWordDetailScreen(dictionaryEntity: DictionaryEntity)


    fun updateWordType()
    fun leftAndRightBtnClicked()
    fun setVisibilitySearchPh(query: String)
    fun setVisibilityAlphabetRec(query: String)



}