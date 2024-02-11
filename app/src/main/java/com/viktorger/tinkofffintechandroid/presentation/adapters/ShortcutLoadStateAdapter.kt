package com.viktorger.tinkofffintechandroid.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.viktorger.tinkofffintechandroid.databinding.LoadStateItemBinding

class ShortcutLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ShortcutLoadStateAdapter.LoadStateViewHolder>() {
    class LoadStateViewHolder(
        private val binding: LoadStateItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState, retry: () -> Unit) {
            if (loadState is LoadState.Error) {
                binding.groupDetailsError.visibility = View.VISIBLE
                binding.btnLoadStateError.setOnClickListener {
                    retry()
                }
            }

            binding.pbDetails.isVisible = loadState is LoadState.Loading
            binding.groupDetailsError.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState, retry)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }
}