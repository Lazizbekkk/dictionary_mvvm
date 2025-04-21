package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.word_details

import androidx.lifecycle.LiveData

interface WordDetailViewModel {
   val showWordUzTextLiveData: LiveData<String>
   val showWordEngTextLiveData: LiveData<String>
   val showNounTextLiveData: LiveData<String>
   val isFavoriteLiveData: LiveData<Int>

   fun showWordDetail(wordNameUz: String, wordNameEng: String, wordNoun: String)
   fun updateFav(id: Int, isFav: Int)
}