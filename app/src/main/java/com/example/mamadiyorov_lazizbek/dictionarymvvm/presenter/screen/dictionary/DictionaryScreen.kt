package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.screen.dictionary

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ScreenDictionaryBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter.AlphabetAdapter
import com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter.DictionaryAdapter

class DictionaryScreen : Fragment(R.layout.screen_dictionary) {

    private val binding: ScreenDictionaryBinding by viewBinding(ScreenDictionaryBinding::bind)
    private val viewModel: DictionaryViewModel by viewModels { DictionaryViewModelFactory() }
    private lateinit var dictionaryAdapter: DictionaryAdapter

    private val REQUEST_RECORD_AUDIO_PERMISSION = 1001
    private val REQUEST_CODE_SPEECH_INPUT = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Kuzatish va navigatsiya
        viewModel.openWordDetailScreen.observe(this) { dictionary ->
            findNavController().navigate(
                DictionaryScreenDirections.actionScreenDictionaryToScreenWordDetail(dictionary)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alphabetList = ('A'..'Z').map { it.toString() }
        val adapter = AlphabetAdapter(alphabetList) { letter ->
            viewModel.searchWord(letter)
            binding.rvList.scrollToPosition(0)
        }
        binding.recAlphabet.adapter = adapter
        binding.recAlphabet.layoutManager = LinearLayoutManager(requireContext())



        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.setVisibilitySearchPH.observe(viewLifecycleOwner) {
            Log.d("LLLLLL", "onViewCreated: setVisibilitySearchPH -> $it")
            if (it) {

                binding.searchVH.visibility = View.VISIBLE
            } else binding.searchVH.visibility = View.INVISIBLE
        }
        // ViewModel kuzatuvchilarni o'rnatish
        viewModel.dictionaryCursorLiveData.observe(viewLifecycleOwner, dictionaryCursorObserver)

        dictionaryAdapter = DictionaryAdapter()

        // RecyclerView sozlash
        viewModel.updateWordType.observe(viewLifecycleOwner) {
            Log.d("TYPE_ADAPTER_TEXT", "updateType viewModel: ${it} keldi")
            if (it == "Uz") {
                binding.flag1Image.setImageResource(R.drawable.ic_uzb)
                binding.flag2Image.setImageResource(R.drawable.ic_eng)
            } else {
                binding.flag1Image.setImageResource(R.drawable.ic_eng)
                binding.flag2Image.setImageResource(R.drawable.ic_uzb)
            }

            dictionaryAdapter.updateType(it)
        }

        viewModel.setVisibilityAlphabetRec.observe(viewLifecycleOwner) {
            if (it) binding.recAlphabet.visibility = View.VISIBLE
            else binding.recAlphabet.visibility = View.GONE
        }
        binding.rvList.adapter = dictionaryAdapter
        dictionaryAdapter.setOnScrollListener(binding.rvList)

        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        val searchView = binding.searchView

        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        searchEditText.setHintTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.search_hint_text
            )
        )

        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.search_text))

        // SearchView ishlovchi
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                dictionaryAdapter.updateQuery(newText)

                viewModel.searchWord(newText)
                viewModel.setVisibilitySearchPh(newText)
                viewModel.setVisibilityAlphabetRec(newText)
                if (newText.isNotEmpty()) binding.rvList.scrollToPosition(0)
                return true
            }
        })

        // Sevimli elementni yangilash
        dictionaryAdapter.setOnFavClickListener { id, isFav ->
            viewModel.updateFav(id, isFav = isFav)
            dictionaryCursorObserver
        }

        // Foydalanuvchi "sevimlilar" sahifasiga o'tadi
        binding.image1.setOnClickListener {
            findNavController().navigate(DictionaryScreenDirections.actionScreenDictionaryToScreenFavoriets())
        }

        // Item bosilganda
        dictionaryAdapter.setSelectedItemClicked {
            viewModel.openWordDetailScreen(it)
        }

        // Nutqdan matn olish uchun
        binding.microfon.setOnClickListener {
            Log.d("LLLLLLLLL", "clicked microfon: ")
            startSpeechToText()
        }
        binding.leftAndRight.setOnClickListener {
            viewModel.leftAndRightBtnClicked()
            viewModel.searchWord(binding.searchView.query.toString())
            viewModel.setVisibilitySearchPh(binding.searchView.query.toString())

        }

        viewModel.updateWordType()



        val scale_bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_bounce)
        binding.image1.startAnimation(scale_bounce)
        binding.image1.postDelayed({
            binding.image1.startAnimation(scale_bounce)
        }, 300) // 300ms — kattalashish davomiyligi
    }

    // Nutqdan matnni qayta ishlash
    fun processSpeechResult(data: Intent?) {
        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val text = results?.get(0) ?: ""
        binding.searchView.setQuery(text, true) // Matnni SearchView'ga o'rnatish
        Toast.makeText(requireContext(), "Gapirilgan matn: $text", Toast.LENGTH_SHORT).show()
    }

    // Nutq tanib olish intenti
    fun getSpeechRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Gapiring...")
        }
    }

    // Ruxsatni tekshirish va so'rash
    private fun checkAudioPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            return false
        }
        return true
    }

    // Nutqdan matn olishni boshlash
    private fun startSpeechToText() {
        if (checkAudioPermission()) {
            val speechIntent = getSpeechRecognizerIntent()
            startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT)
        }
    }

    // Ruxsat natijasini qayta ishlash
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSpeechToText()
            } else {
                Toast.makeText(requireContext(), "Ruxsat talab qilinadi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Nutq natijasini olish
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == -1) { // Activity.RESULT_OK
            processSpeechResult(data)
        }
    }

    // Cursor kuzatuvchi
    private val dictionaryCursorObserver = Observer<Cursor> { cursor ->
        dictionaryAdapter.setCursor(cursor)
    }


}
