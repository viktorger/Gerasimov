package com.viktorger.tinkofffintechandroid.presentation.popular

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.RemoteMediator
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentPopularBinding
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutAdapter
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            onLongClick = {
                vm.addToFavorite(it)
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
        // vm.loadNetworkData()
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
    }

    private fun initListeners() {
        /*vm.movieListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }*/
        lifecycleScope.launch {
            vm.movieShortcutStateFlow.collectLatest {
                adapter.submitData(it)
            }
        }
        binding.btnPopularError.setOnClickListener {
            adapter.retry()
        }
    }

}