package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.dictionary

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.AppRepository

class DictionaryViewModelImpl(private val repository: AppRepository) : DictionaryViewModel,
    ViewModel() {
    private var searchText = ""
    override val openWordDetailScreen: MutableLiveData<DictionaryEntity> = MutableLiveData()
    override val dictionaryCursorLiveData: MutableLiveData<Cursor> =
        MutableLiveData(repository.getCursor())
    override val openWordDetailsScreen: MutableLiveData<Int> = MutableLiveData()
    override val updateWordType: MutableLiveData<String> = MutableLiveData()
    override val setVisibilitySearchPH: MutableLiveData<Boolean> = MutableLiveData()
    override val setVisibilityAlphabetRec: MutableLiveData<Boolean> = MutableLiveData()

    override fun searchWord(query: String) {
        searchText = query
        dictionaryCursorLiveData.value = if (query.isEmpty()) {
            setVisibilitySearchPH.value = false
            repository.getCursor()
        }
        else {
            repository.searchWord(query)

        }
    }

    override fun updateFav(id: Int, isFav: Int) {
        if(isFav > 1) {
            repository.changeFavById(id, 0)
            dictionaryCursorLiveData.value = if (searchText.isEmpty()) repository.getCursor()
            else {
                repository.searchWord(searchText)
            }
        }
        else {
        repository.changeFavById(id, isFav xor 1)
        dictionaryCursorLiveData.value = if (searchText.isEmpty()) repository.getCursor()
        else {
            repository.searchWord(searchText)
        }
        }
    }

    override fun openWordDetailScreen(dictionaryEntity: DictionaryEntity) {
        openWordDetailScreen.value = dictionaryEntity
    }

    override fun updateWordType() {
        updateWordType.value = repository.getType()
    }

    override fun leftAndRightBtnClicked() {
        if (repository.getType() == "Uz") {
            repository.setType("Eng")
        } else {
            repository.setType("Uz")
        }
        updateWordType()
    }

    override fun setVisibilitySearchPh(query: String) {
            if(query.isNotEmpty()){
                setVisibilitySearchPH.value = repository.searchWord(searchText).count == 0
            }
    }

    override fun setVisibilityAlphabetRec(query: String) {
       setVisibilityAlphabetRec.value = query.isEmpty()
    }


}