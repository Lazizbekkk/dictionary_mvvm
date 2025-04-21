package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.AppRepository

class MenuViewModelImpl(private val repository: AppRepository) : MenuViewModel, ViewModel() {
    override val openWordsScreenLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun engUz() {
        repository.setType("Eng")
        openWordsScreenLiveData.value = Unit
    }

    override fun uzEng() {
        repository.setType("Uz")
        openWordsScreenLiveData.value = Unit
    }
}