package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.word_details

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ScreenWordDetailsBinding
import java.util.Locale

class WordDetailScreen : Fragment(R.layout.screen_word_details) {
    private val binding: ScreenWordDetailsBinding by viewBinding(ScreenWordDetailsBinding::bind)
    private val viewModel: WordDetailViewModel by viewModels { WordViewModelFactory() }
    private lateinit var textToSpeech: TextToSpeech
    private val args: WordDetailScreenArgs by navArgs()
    private var isFav = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US
            }
        }
        viewModel.showWordUzTextLiveData.observe(viewLifecycleOwner) {
            binding.wordName.text = it
        }
        viewModel.showWordEngTextLiveData.observe(viewLifecycleOwner) {
            binding.wordTranslate.text = it
        }
        viewModel.showNounTextLiveData.observe(viewLifecycleOwner) {
            binding.wordType.text = it
        }
        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) {
            isFav = it
            if (isFav == 0) {
                binding.starDetail.setImageResource(R.drawable.yulduz2)
            } else {
                binding.starDetail.setImageResource(R.drawable.yulduz1)
            }
        }

        viewModel.showWordDetail(
            wordNameUz = args.dictionaryData.uzbek.toString(),
            wordNameEng = args.dictionaryData.english.toString(),
            wordNoun = args.dictionaryData.type.toString()
        )
        isFav = args.dictionaryData.isFavourite ?: 0
        if (isFav == 0) {
            binding.starDetail.setImageResource(R.drawable.yulduz2)
        } else {
            binding.starDetail.setImageResource(R.drawable.yulduz1)
        }

        binding.spic.setOnClickListener {
            speakOut(binding.wordName.text.toString())
        }
        binding.starDetail.setOnClickListener {
            viewModel.updateFav(args.dictionaryData.id, isFav)
        }
        val scale_bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_spic
        )
        binding.spic.startAnimation(scale_bounce)
        binding.spic.postDelayed({
            binding.spic.startAnimation(scale_bounce)
        }, 500) // 300ms — kattalashish davomiyligi
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}
