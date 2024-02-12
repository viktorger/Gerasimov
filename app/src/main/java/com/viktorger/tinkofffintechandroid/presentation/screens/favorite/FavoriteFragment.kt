package com.viktorger.tinkofffintechandroid.presentation.screens.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentFavoriteBinding
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutAdapter
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutPagingDataAdapter
import com.viktorger.tinkofffintechandroid.presentation.model.NetworkStatus
import com.viktorger.tinkofffintechandroid.presentation.screens.popular.PopularFragmentDirections
import com.viktorger.tinkofffintechandroid.presentation.screens.popular.PopularViewModel
import com.viktorger.tinkofffintechandroid.presentation.screens.popular.PopularViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: FavoriteViewModelFactory
    private val vm: FavoriteViewModel by viewModels { viewModelFactory }

    private val adapter: ShortcutAdapter by lazy {
        ShortcutAdapter(
            onClick = {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailsFragment(
                        it, NetworkStatus.Offline
                    )
                findNavController().navigate(action)
            },
            onLongClick = { movieShortcut, position ->

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
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
    }

    private fun initListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.movieShortcutStateFlow.collectLatest {
                    if (it is ResultModel.Success) {
                        adapter.submitList(it.data)
                    }

                    binding.pbFavorite.isGone = it !is ResultModel.Loading
                    binding.rvFavorite.isGone = it !is ResultModel.Success
                    binding.groupFavoriteError.isGone = it !is ResultModel.Error
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvFavorite.adapter = adapter
    }

}