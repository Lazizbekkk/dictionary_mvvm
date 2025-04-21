package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.DialogBinding

class MenuScreen : Fragment(R.layout.screen_menu) {
    private var _binding: DialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels { MenuViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openWordsScreenLiveData.observe(this) {
            findNavController().navigate(MenuScreenDirections.actionHomeScreenToScreenDictionary())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.UzEng).setOnClickListener {
            viewModel.uzEng()
        }

        view.findViewById<CardView>(R.id.EngUz).setOnClickListener {
            viewModel.engUz()
        }


        binding.image.button1.setOnClickListener {
            binding.layout.openDrawer(GravityCompat.START)
        }

        binding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.app_info -> {
                    findNavController().navigate(MenuScreenDirections.actionHomeScreenToScreenInfo())
                }

                R.id.share -> {
                    shareApp()
                }

                R.id.cantact -> {
                    contactUs()
                }

                R.id.exit -> {
                    finishDialog()
                }
            }
            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finishDialog()
                }
            }
        )
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareApp() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this cool app: https://yourappurl.com")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Share via"))
    }

    private fun contactUs() {
        try {
            val telegramUri = Uri.parse("https://t.me/laziz_255")
            val telegramIntent = Intent(Intent.ACTION_VIEW, telegramUri)
            startActivity(telegramIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Telegram app not installed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun finishDialog() {
        findNavController().navigate(MenuScreenDirections.actionHomeScreenToExitDialog())
    }
}
