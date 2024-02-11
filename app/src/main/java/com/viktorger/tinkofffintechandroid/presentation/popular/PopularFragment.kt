package com.viktorger.tinkofffintechandroid.presentation.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentPopularBinding
import com.viktorger.tinkofffintechandroid.presentation.adapters.ShortcutAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PopularViewModelFactory
    private val vm: PopularViewModel by viewModels { viewModelFactory }

    private val adapter: ShortcutAdapter by lazy { ShortcutAdapter {
        val action = PopularFragmentDirections.actionPopularFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.rvPopular.adapter = adapter
    }

    private fun initListeners() {
        /*vm.movieListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }*/
        lifecycleScope.launch {
            vm.movieShortcutFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

}