package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ItemWordBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.utils.DictionaryConstants

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.DictionaryVH>() {
    private var cursor: Cursor? = null
    private var favListener: ((Int, Int) -> Unit)? = null
    private var selectedItemClicked: ((DictionaryEntity) -> Unit)? = null

    private var type: String = ""
    private var query: String = ""

    fun updateQuery(newQuery: String) {
        query = newQuery
        notifyDataSetChanged()
    }
    private var isItemClicked = false


    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    fun updateType(type: String){
        Log.d("TYPE_ADAPTER_TEXT", "updateType: ${type} keldi")
        this.type = type
        notifyDataSetChanged()
    }

    inner class DictionaryVH(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.setOnClickListener {
                isItemClicked = true
                cursor?.let {
                    it.moveToPosition(adapterPosition)
                    favListener?.invoke(
                        it.getInt(it.getColumnIndex("id") ?: 0),
                        it.getInt(it.getColumnIndex("is_favourite") ?: 0)
                    )
                }
            }

            binding.root.setOnClickListener {
                isItemClicked = true
                cursor?.let { cursor ->
                    cursor.moveToPosition(adapterPosition) // Hozirgi pozitsiyani kursorga o'rnatish

                    selectedItemClicked?.invoke(
                        DictionaryEntity(
                            id = cursor.getInt(cursor.getColumnIndex("id") ?: -1),
                            uzbek = cursor.getString(
                                cursor.getColumnIndex(
                                    DictionaryConstants.LANGUAGE_UZ
                                ) ?: 0
                            ),
                            type = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.TYPE) ?: 0
                            ),
                            isFavourite = cursor.getInt(
                                cursor.getColumnIndex(
                                    DictionaryConstants.FAVOURITE
                                ) ?: 0
                            ),
                            english = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.LANGUAGE_ENG) ?: 0
                            ),
                            transcript = cursor.getString(
                                cursor.getColumnIndex(
                                    DictionaryConstants.TRANSCRIPT
                                ) ?: 0
                            ),
                            countable = cursor.getString(
                                cursor.getColumnIndex(
                                    DictionaryConstants.COUNTABLE
                                ) ?: 0
                            )
                        )
                    )
                }
            }
        }
        fun bind(query: String) {
            cursor?.let { cursor ->
                cursor.moveToPosition(adapterPosition)
                val word = if (type == "Uz") {
                    cursor.getString(cursor.getColumnIndex("uzbek") ?: 0)
                } else {
                    cursor.getString(cursor.getColumnIndex("english") ?: 0)
                }

                val highlightedText = getHighlightedText(word, query)
                binding.wordName.text = highlightedText // Spannable matnni o'rnatish

                binding.wordType.text = cursor.getString(cursor.getColumnIndex("type") ?: 0)
                val isFavorite = cursor.getInt(cursor.getColumnIndex("is_favourite") ?: 0)
                binding.image.setImageResource(if (isFavorite == 0) R.drawable.yulduz2 else R.drawable.yulduz1)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryVH =
        DictionaryVH(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = cursor?.count ?: 0

    override fun onBindViewHolder(holder: DictionaryVH, position: Int) {
        holder.bind(query)
        if(!isItemClicked) animateLetter(holder.itemView)
    }

    fun setOnFavClickListener(l: (Int, Int) -> Unit) {
        favListener = l
    }



    fun setSelectedItemClicked(l: (DictionaryEntity) -> Unit) {
        selectedItemClicked = l
    }

    fun getHighlightedText(fullText: String, query: String): Spannable {
        val spannable = SpannableString(fullText)
        val startIndex = fullText.lowercase().indexOf(query.lowercase())
        if (startIndex != -1) {
            val endIndex = startIndex + query.length
            spannable.setSpan(
                ForegroundColorSpan(Color.BLUE), // Rangni belgilash
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
    private fun animateLetter(view: View) {
        // Animate the scaling from left to right (scaleX) and from top to bottom (scaleY)
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.2f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 1.0f)

        // Animate translation from left to right (translationX)
        val translationX = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, translationX)
        animatorSet.duration = 500  // You can adjust the duration for a smoother transition
        animatorSet.start()
    }
    fun setOnScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isItemClicked = false
            }
        })
    }



}