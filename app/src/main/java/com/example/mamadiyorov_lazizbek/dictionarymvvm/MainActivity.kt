package com.example.mamadiyorov_lazizbek.dictionarymvvm

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val windowInsetsController = window.insetsController
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = window.insetsController
            window.statusBarColor = ContextCompat.getColor(this, R.color.app_color) // Status bar foni

            windowInsetsController?.setSystemBarsAppearance(
                0, // Bu flaglarni olib tashlaydi, ikonalar oq rangda bo‘ladi
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = 0 // Hech qanday `LIGHT_STATUS_BAR` flag qo‘shilmaydi
            window.statusBarColor = ContextCompat.getColor(this, R.color.app_color) // Status bar foni
        }
    }
}
