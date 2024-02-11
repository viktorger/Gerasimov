package com.viktorger.tinkofffintechandroid.presentation.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
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
                        vm.getDetails(args.movieId)
                        binding.btnDetailsError.setOnClickListener(null)
                    }
                }
            }
        }
    }

    private fun getShimmerDrawable(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

// This is the placeholder for the imageView
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        return shimmerDrawable
    }
}