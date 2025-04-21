package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.favoriet

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.AppRepository

class FavoritesViewModelImpl(private val repository: AppRepository) : FavorietsViewModel, ViewModel() {
    override val openWordDetailScreen: MutableLiveData<DictionaryEntity> = MutableLiveData()
    override val favoritesCursorLiveData: MutableLiveData<Cursor> =
        MutableLiveData(repository.getCursorByFavorites())
    override val itemClicked: MutableLiveData<Int> = MutableLiveData()
    override val updateTypeLiveData: MutableLiveData<String> = MutableLiveData()
    override val setVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun updateFav(id: Int, isFav: Int) {
        repository.changeFavById(id, isFav xor 1)
        repository.getCursorByFavorites()
    }

    override fun itemClicked(idInt: Int) {
        itemClicked.value = idInt

        repository.changeFavById(
            id = idInt,
            fav = 0
        )
        repository.getCursorByFavorites()

        getAllCursorFavorites()
    }

    override fun getAllCursorFavorites(): Cursor {
        setVisibilityLiveData.value = repository.getCursorByFavorites().count == 0
        return repository.getCursorByFavorites()
    }

    override fun openWordDetailScreen(dictionaryEntity: DictionaryEntity) {
        openWordDetailScreen.value = dictionaryEntity
    }

    override fun updateType() {
       updateTypeLiveData.value = repository.getType()
    }
}