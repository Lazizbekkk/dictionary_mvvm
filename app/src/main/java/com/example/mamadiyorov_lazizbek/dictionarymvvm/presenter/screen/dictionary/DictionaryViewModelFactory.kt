package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl.AppRepositoryImpl

class DictionaryViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DictionaryViewModelImpl(AppRepositoryImpl.getRepository())  as T
    }
}