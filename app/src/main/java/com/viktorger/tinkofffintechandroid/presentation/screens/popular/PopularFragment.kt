package com.viktorger.tinkofffintechandroid.presentation.screens.popular

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentPopularBinding
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutPagingDataAdapter
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SHOULD_NOT_UPDATE = -1

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PopularViewModelFactory
    private val vm: PopularViewModel by viewModels { viewModelFactory }

    private val adapter: ShortcutPagingDataAdapter by lazy {
        ShortcutPagingDataAdapter(
            onClick = {
                val action =
                    PopularFragmentDirections.actionPopularFragmentToMovieDetailsFragment(it)
                findNavController().navigate(action)
            },
            onLongClick = { movieShortcut, position ->
                if (!movieShortcut.isFavorite) vm.addToFavorite(movieShortcut, position)
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TFApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecycler()
    }

    private fun initRecycler() {
        val retry = {
            adapter.retry()
        }
        adapter.addLoadStateListener {
            val refreshState = it.refresh

            binding.groupFavoriteError.isGone = refreshState !is LoadState.Error
            binding.pbFavorite.isGone = refreshState !is LoadState.Loading
            binding.rvFavorite.isGone = refreshState !is LoadState.NotLoading
        }
        binding.rvFavorite.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ShortcutLoadStateAdapter(retry),
            footer = ShortcutLoadStateAdapter(retry)
        )

        binding.rvFavorite.setItemAnimator(null);

    }

    private fun initListeners() {
        binding.btnFavoriteError.setOnClickListener {
            adapter.retry()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.movieShortcutStateFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.shouldUpdateItem.collectLatest {
                    if (it.number != SHOULD_NOT_UPDATE) {
                        adapter.notifyItemChanged(it.number)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_text_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        vm.setShouldNotUpdate()
                    }
                }
            }
        }
    }

}