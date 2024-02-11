package com.viktorger.tinkofffintechandroid.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.TFApplication
import com.viktorger.tinkofffintechandroid.databinding.FragmentFavoriteBinding
import com.viktorger.tinkofffintechandroid.databinding.FragmentMovieDetailsBinding
import com.viktorger.tinkofffintechandroid.model.ResultModel
import com.viktorger.tinkofffintechandroid.presentation.popular.PopularViewModel
import com.viktorger.tinkofffintechandroid.presentation.popular.PopularViewModelFactory
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory
    private val vm: MovieDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as TFApplication).appComponent.inject(this)
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
        vm.getDetails(args.movieId)
    }

    private fun initListeners() {
        vm.detailsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultModel.Success -> {
                    with(it.data) {
                        Glide
                            .with(requireContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.movie_image)
                            .skipMemoryCache(true) // for caching the image url in case phone is offline
                            .into(binding.ivDetails)

                        binding.tvDetailsTitle.text = title
                        binding.tvDetailsDesc.text = description
                        binding.tvDetailsGenresList.text = genres
                        binding.tvDetailsCountriesList.text = countries
                    }
                }
                is ResultModel.Loading -> {

                }
                is ResultModel.Error -> {

                }
            }
        }
    }
}