package com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.dao.DictionaryDao
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.shared_pref.SharedPref
import com.example.mamadiyorov_lazizbek.dictionarymvvm.repository.AppRepository

class AppRepositoryImpl(private val dictionaryDao: DictionaryDao) : AppRepository {

    companion object {
        private lateinit var appRepository: AppRepository

        fun init(dictionaryDao: DictionaryDao) {
            if (!::appRepository.isInitialized) {
                appRepository =
                    AppRepositoryImpl(dictionaryDao)
            }
        }

        fun getRepository(): AppRepository = appRepository

    }

    override fun getCursor(): Cursor {
        return if (getType() == "Uz") dictionaryDao.getAllUzb() else dictionaryDao.getAllEng()
    }

    override fun changeFavById(id: Int, fav: Int) {
        dictionaryDao.updateFav(id, fav)
    }

    override fun getWordById(id: Int): LiveData<DictionaryEntity> = dictionaryDao.getWordById(id)

    override fun update(dictionaryEntity: DictionaryEntity) {
        dictionaryDao.insert(dictionaryEntity)
    }

    override fun searchWord(query: String): Cursor =
        if (getType() == "Uz") dictionaryDao.getSearchWordsUz(query)
        else dictionaryDao.getSearchWordsEng(query)

    override fun getCursorByFavorites(): Cursor =
        if (getType() == "Uz") dictionaryDao.getFavoriteWordsUz() else dictionaryDao.getFavoriteWordsEng()

    override fun setType(type: String) {

        SharedPref.getSharedPref().edit().putString("TYPE_WORD", type).apply()
    }

    override fun getType(): String {

        return SharedPref.getSharedPref().getString("TYPE_WORD", "Eng").toString()
    }

}