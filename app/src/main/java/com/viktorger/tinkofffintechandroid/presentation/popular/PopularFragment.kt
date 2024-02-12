package com.viktorger.tinkofffintechandroid.presentation.popular

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.paging.RemoteMediator
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentPopularBinding
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutAdapter
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

const val SHOULD_NOT_UPDATE = -1

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PopularViewModelFactory
    private val vm: PopularViewModel by viewModels { viewModelFactory }

    private val adapter: ShortcutAdapter by lazy {
        ShortcutAdapter(
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

            binding.groupPopularError.isGone = refreshState !is LoadState.Error
            binding.pbPopular.isGone = refreshState !is LoadState.Loading
            binding.rvPopular.isGone = refreshState !is LoadState.NotLoading
        }
        binding.rvPopular.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ShortcutLoadStateAdapter(retry),
            footer = ShortcutLoadStateAdapter(retry)
        )

        binding.rvPopular.setItemAnimator(null);

    }

    private fun initListeners() {
        binding.btnPopularError.setOnClickListener {
            adapter.retry()
        }

        lifecycleScope.launch {
            vm.movieShortcutStateFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
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