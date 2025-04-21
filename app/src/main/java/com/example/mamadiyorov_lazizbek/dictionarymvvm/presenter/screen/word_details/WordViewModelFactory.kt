package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.word_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl.AppRepositoryImpl

class WordViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WordDetailViewModelImpl(AppRepositoryImpl.getRepository()) as T
    }
}