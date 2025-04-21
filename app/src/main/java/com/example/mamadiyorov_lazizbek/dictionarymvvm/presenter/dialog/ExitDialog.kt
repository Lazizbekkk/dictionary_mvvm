package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.dialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.DialogFinishAppBinding

class ExitDialog : DialogFragment(R.layout.dialog_finish_app) {

    private val binding: DialogFinishAppBinding by viewBinding(DialogFinishAppBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Ha" tugmasi uchun listener
        binding.yesBtn.setOnClickListener {
            requireActivity().finish()
            dismiss() // Dialogni yopish
        }

        // "Yo'q" tugmasi uchun listener
        binding.noBtn.setOnClickListener {
            dismiss() // Faqat dialogni yopish
        }
    }

    override fun onStart() {
        super.onStart()
        // Dialog oynasining o'lchamini va foni sozlash
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
