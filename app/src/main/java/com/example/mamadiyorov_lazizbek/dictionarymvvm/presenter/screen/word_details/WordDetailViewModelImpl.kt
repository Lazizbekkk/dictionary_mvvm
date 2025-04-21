package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.word_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.AppRepository

class WordDetailViewModelImpl(private val repository: AppRepository) : WordDetailViewModel,
    ViewModel() {
    override val showWordUzTextLiveData: MutableLiveData<String> = MutableLiveData()
    override val showWordEngTextLiveData: MutableLiveData<String> = MutableLiveData()
    override val showNounTextLiveData: MutableLiveData<String> = MutableLiveData()
    override val isFavoriteLiveData: MutableLiveData<Int> = MutableLiveData()

    override fun showWordDetail(wordNameUz: String, wordNameEng: String, wordNoun: String) {
        if (repository.getType() == "Uz") {
            showWordUzTextLiveData.value = wordNameUz
            showWordEngTextLiveData.value = wordNameEng
        } else {
            showWordUzTextLiveData.value = wordNameEng
            showWordEngTextLiveData.value = wordNameUz
        }
        showNounTextLiveData.value = wordNoun
    }

    override fun updateFav(id: Int, isFav: Int) {
        isFavoriteLiveData.value = isFav xor 1
        repository.changeFavById(id = id, fav = isFav xor 1)
    }
}