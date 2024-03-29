package com.viktorger.tinkofffintechandroid.presentation.screens.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentMovieDetailsBinding
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.presentation.common.getShimmerDrawable
import com.viktorger.tinkofffintechandroid.presentation.screens.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory
    private val vm: MovieDetailsViewModel by viewModels { viewModelFactory }

    private val vmActivity: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TFApplication).appComponent.inject(this)
        vmActivity.shouldShowButtons(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        vm.getDetails(args.movieId, args.networkStatus)
    }

    private fun initListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.movieDetailsStateFlow.collect() {
                    when (it) {
                        is ResultModel.Success -> {
                            with(it.data) {
                                Glide
                                    .with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(getShimmerDrawable())
                                    .skipMemoryCache(true) // for caching the image url in case phone is offline
                                    .into(binding.ivDetails)

                                binding.tvDetailsTitle.text = title
                                binding.tvDetailsDesc.text = description
                                binding.tvDetailsGenresList.text = genres
                                binding.tvDetailsCountriesList.text = countries
                            }
                            binding.pbDetails.visibility = View.GONE
                            binding.svDetails.visibility = View.VISIBLE
                        }

                        is ResultModel.Loading -> {
                            binding.groupDetailsError.visibility = View.GONE
                            binding.pbDetails.visibility = View.VISIBLE
                        }

                        is ResultModel.Error -> {
                            binding.pbDetails.visibility = View.GONE
                            binding.groupDetailsError.visibility = View.VISIBLE

                            binding.btnDetailsError.setOnClickListener {
                                vm.getDetails(args.movieId, args.networkStatus)
                                binding.btnDetailsError.setOnClickListener(null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        vmActivity.shouldShowButtons(true)
    }
}