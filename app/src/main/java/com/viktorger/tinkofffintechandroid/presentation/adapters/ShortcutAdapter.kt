package com.viktorger.tinkofffintechandroid.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.shape.CornerFamily
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.databinding.ItemMovieShortcutBinding
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.presentation.getShimmerDrawable

class ShortcutAdapter(
    private val onClick: (id: Int) -> Unit
) : PagingDataAdapter<MovieShortcut, ShortcutAdapter.ViewHolder>(UserDiffCallBack) {

    object UserDiffCallBack : DiffUtil.ItemCallback<MovieShortcut>() {
        override fun areItemsTheSame(oldItem: MovieShortcut, newItem: MovieShortcut): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieShortcut, newItem: MovieShortcut): Boolean =
            oldItem == newItem
    }

    class ViewHolder(
        private val binding: ItemMovieShortcutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieShortcut: MovieShortcut?, onClick: (id: Int) -> Unit) {
            movieShortcut?.let {
                binding.root.setOnClickListener {
                    onClick(movieShortcut.id)
                }
                binding.tvShortcutTitle.text = movieShortcut.title
                binding.tvShortcutDate.text = movieShortcut.releaseDate

                Glide.with(binding.root.context)
                    .load(movieShortcut.imageUrl)
                    .placeholder(getShimmerDrawable())
                    .skipMemoryCache(true) // for caching the image url in case phone is offline
                    .into(binding.sivShortcut)
            }

        }
        fun dpToPx(context: Context, dp: Int): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemMovieShortcutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}