package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.info

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class ScreenInfoViewModel : ViewModel() {

    fun getTelegramIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/gitauz"))
        intent.setPackage("org.telegram.messenger")
        return intent
    }

    fun getInstagramIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/gita.uz"))
        intent.setPackage("com.instagram.android")
        return intent
    }

    fun getGitaIntent(): Intent {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/search?q=gita+dasturchilar+akademiyasi")
        )
    }
}
