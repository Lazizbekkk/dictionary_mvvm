package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R

class AlphabetAdapter(
    private val alphabetList: List<String>,
    private val onLetterClick: (String) -> Unit
) : RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

    inner class AlphabetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_letter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alphabet, parent, false)
        return AlphabetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        holder.textView.text = alphabetList[position]
        holder.itemView.setOnClickListener {
            onLetterClick(alphabetList[position])
            animateLetter(holder.itemView)
        }
        animateLetter2(holder.itemView)
    }

    override fun getItemCount(): Int = alphabetList.size
    private fun animateLetter(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 4f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 4f, 1.0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 500
        animatorSet.start()
    }

    private fun animateLetter2(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.2f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 1.0f)

        val translationX = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, translationX)
        animatorSet.duration = 500  // You can adjust the duration for a smoother transition
        animatorSet.start()
    }
}
