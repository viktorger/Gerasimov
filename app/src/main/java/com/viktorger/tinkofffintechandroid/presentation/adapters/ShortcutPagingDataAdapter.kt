package com.viktorger.tinkofffintechandroid.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viktorger.tinkofffintechandroid.databinding.ItemMovieShortcutBinding
import com.viktorger.tinkofffintechandroid.model.MovieShortcut
import com.viktorger.tinkofffintechandroid.presentation.common.getShimmerDrawable

class ShortcutPagingDataAdapter(
    private val onClick: (id: Int) -> Unit,
    private val onLongClick: (movieShortcut: MovieShortcut, position: Int) -> Unit
) : PagingDataAdapter<MovieShortcut, ShortcutPagingDataAdapter.ViewHolder>(UserDiffCallBack) {


    object UserDiffCallBack : DiffUtil.ItemCallback<MovieShortcut>() {
        override fun areItemsTheSame(oldItem: MovieShortcut, newItem: MovieShortcut): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieShortcut, newItem: MovieShortcut): Boolean =
            oldItem == newItem
    }

    inner class ViewHolder(
        private val binding: ItemMovieShortcutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            onClick: (id: Int) -> Unit,
            onLongClick: (movieShortcut: MovieShortcut, position: Int) -> Unit,
        ) {
            getItem(position)?.let { movieShortcut ->
                binding.root.setOnClickListener {
                    onClick(movieShortcut.id)
                }
                binding.root.setOnLongClickListener {
                    onLongClick(movieShortcut, position)
                    binding.ivShortcutStar.visibility = View.VISIBLE
                    true
                }
                binding.tvShortcutTitle.text = movieShortcut.title
                binding.tvShortcutDate.text = movieShortcut.releaseDate
                binding.ivShortcutStar.visibility = if (movieShortcut.isFavorite) View.VISIBLE else View.INVISIBLE

                Glide.with(binding.root.context)
                    .load(movieShortcut.imageUrl)
                    .placeholder(getShimmerDrawable())
                    .skipMemoryCache(true) // for caching the image url in case phone is offline
                    .into(binding.sivShortcut)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemMovieShortcutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(position, onClick, onLongClick)

}