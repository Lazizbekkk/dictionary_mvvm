package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ScreenInfoBinding

class ScreenInfo : Fragment(R.layout.screen_info) {

    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val viewModel: ScreenInfoViewModel by viewModels()

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Orqaga qaytish tugmasi
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Telegram havolasini ochish
        binding.btnTelegram.setOnClickListener {
            try {
                startActivity(viewModel.getTelegramIntent())
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/gitauz")))
            }
        }

        // Instagram havolasini ochish
        binding.btnInstagram.setOnClickListener {
            try {
                startActivity(viewModel.getInstagramIntent())
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.instagram.com/gita.uz")
                    )
                )
            }
        }

        // GITA haqida ma'lumotni ochish
        binding.btnGita.setOnClickListener {
            try {
                startActivity(viewModel.getGitaIntent())
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "Browser not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
