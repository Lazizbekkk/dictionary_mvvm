package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.favoriet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl.AppRepositoryImpl

class FavorietsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModelImpl(AppRepositoryImpl.getRepository()) as T
    }
}