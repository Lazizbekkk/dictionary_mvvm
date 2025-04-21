package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.menu

import androidx.lifecycle.LiveData

interface MenuViewModel {
    val openWordsScreenLiveData: LiveData<Unit>
    fun engUz()
    fun uzEng()
}