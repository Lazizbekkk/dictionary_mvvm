package com.example.mamadiyorov_lazizbek.dictionarymvvm.presenter.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mamadiyorov_lazizbek.dictionarymvvm.R
import com.example.mamadiyorov_lazizbek.dictionarymvvm.data.source.local.room.entities.DictionaryEntity
import com.example.mamadiyorov_lazizbek.dictionarymvvm.databinding.ItemFavoritesBinding
import com.example.mamadiyorov_lazizbek.dictionarymvvm.utils.DictionaryConstants

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesVH>() {
    private var cursor: Cursor? = null

    private var isItemClicked = false


    private var itemClicked: ((Int) -> Unit)? = null
    private var selectedItemClicked: ((DictionaryEntity) -> Unit)? = null

    private var type: String = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setFavCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }


    fun updateType(type: String) {
        this.type = type
    }

    inner class FavoritesVH(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                isItemClicked = true
                cursor?.let { cursor ->
                    cursor.moveToPosition(adapterPosition) // Hozirgi pozitsiyani kursorga o'rnatish

                    // Ma'lumotlarni olib, obyekt yaratish
                    selectedItemClicked?.invoke(
                        DictionaryEntity(
                            id = cursor.getInt(cursor.getColumnIndex("id") ?: -1),
                            uzbek = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.LANGUAGE_UZ) ?: 0
                            ),
                            type = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.TYPE) ?: 0
                            ),
                            isFavourite = cursor.getInt(
                                cursor.getColumnIndex(DictionaryConstants.FAVOURITE) ?: 0
                            ),
                            english = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.LANGUAGE_ENG) ?: 0
                            ),
                            transcript = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.TRANSCRIPT) ?: 0
                            ),
                            countable = cursor.getString(
                                cursor.getColumnIndex(DictionaryConstants.COUNTABLE) ?: 0
                            )
                        )
                    )
                }
            }

            binding.image.setOnClickListener {
                isItemClicked = true
                cursor?.let { cursor ->
                    cursor.moveToPosition(adapterPosition)
                    itemClicked?.invoke(cursor.getInt(cursor.getColumnIndex("id") ?: 0))
                }
            }
        }


        fun bind() {
            if (type == "Uz") {
                cursor?.let { cursor ->
                    cursor.moveToPosition(adapterPosition)
                    binding.wordName.text =
                        cursor.getString(
                            cursor.getColumnIndex(DictionaryConstants.LANGUAGE_UZ) ?: 0
                        )
                    binding.wordType.text =
                        cursor.getString(cursor.getColumnIndex(DictionaryConstants.TYPE) ?: 0)
                    val isFavourite =
                        cursor.getInt(cursor.getColumnIndex(DictionaryConstants.FAVOURITE) ?: 0)
                    binding.image.setImageResource(
                        if (isFavourite == 0) R.drawable.yulduz2 else R.drawable.yulduz1
                    )
                }
            } else {
                cursor?.let { cursor ->
                    cursor.moveToPosition(adapterPosition)
                    binding.wordName.text =
                        cursor.getString(
                            cursor.getColumnIndex(DictionaryConstants.LANGUAGE_ENG) ?: 0
                        )
                    binding.wordType.text =
                        cursor.getString(cursor.getColumnIndex(DictionaryConstants.TYPE) ?: 0)
                    val isFavourite =
                        cursor.getInt(cursor.getColumnIndex(DictionaryConstants.FAVOURITE) ?: 0)
                    binding.image.setImageResource(
                        if (isFavourite == 0) R.drawable.yulduz2 else R.drawable.yulduz1
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH =
        FavoritesVH(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = cursor?.count ?: 0

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        holder.bind()
        if(!isItemClicked) animateLetter(holder.itemView)
    }

    fun setItemClicked(l: ((Int) -> Unit)) {
        itemClicked = l
    }

    fun setSelectedItemClicked(l: (DictionaryEntity) -> Unit) {
        selectedItemClicked = l
    }
    fun setOnScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isItemClicked = false
            }
        })
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
}