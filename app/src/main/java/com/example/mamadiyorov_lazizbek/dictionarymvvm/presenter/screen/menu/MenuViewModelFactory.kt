package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl.AppRepositoryImpl

class MenuViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MenuViewModelImpl(AppRepositoryImpl.getRepository()) as T

    }
}