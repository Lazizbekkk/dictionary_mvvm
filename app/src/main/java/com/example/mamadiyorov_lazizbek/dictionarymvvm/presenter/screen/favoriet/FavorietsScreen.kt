package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.favoriet

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ScreenFavoritesBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter.FavoritesAdapter

class FavorietsScreen : Fragment(R.layout.screen_favorites) {
    private val binding: ScreenFavoritesBinding by viewBinding(ScreenFavoritesBinding::bind)
    private val viewModel: FavorietsViewModel by viewModels { FavorietsViewModelFactory() }
    private val adapter: FavoritesAdapter by lazy { FavoritesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openWordDetailScreen.observe(this) { dictionary ->
            findNavController().navigate(
                FavorietsScreenDirections.actionScreenFavorietsToScreenWordDetail(
                    dictionaryData = dictionary
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("KKKKKKK", "onViewCreated: FavorietsScreen")
        Log.d("KKKKKKK", "getAllWords: ${viewModel.getAllCursorFavorites()}")

        viewModel.setVisibilityLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.emptyImage.visibility = View.VISIBLE
            } else binding.emptyImage.visibility = View.GONE
        }

        viewModel.updateTypeLiveData.observe(viewLifecycleOwner) {
            adapter.updateType(it)
        }

        adapter.setSelectedItemClicked {
            viewModel.openWordDetailScreen(it)
        }

        viewModel.favoritesCursorLiveData.observe(viewLifecycleOwner) {
            viewModel.getAllCursorFavorites()
            adapter.setFavCursor(it)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        adapter.setItemClicked {
            viewModel.updateFav(it, 1)
            adapter.setFavCursor(viewModel.getAllCursorFavorites())
        }
        viewModel.updateType()

        viewModel.getAllCursorFavorites()

        adapter.setFavCursor(viewModel.getAllCursorFavorites())
        binding.list.adapter = adapter
        adapter.setOnScrollListener(binding.list)
        binding.list.layoutManager = LinearLayoutManager(requireContext())


    }

}