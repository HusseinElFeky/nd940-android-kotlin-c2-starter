package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.common.Constants.MEDIA_TYPE_IMAGE
import com.udacity.asteroidradar.common.NetworkState
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var asteroidsAdapter: AsteroidsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        initRecyclerView()
        initObservables()

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding.asteroidRecycler) {
            setHasFixedSize(true)
            asteroidsAdapter = AsteroidsAdapter {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }
            adapter = asteroidsAdapter
        }
    }

    private fun initObservables() {
        viewModel.asteroidsLiveData.observe(viewLifecycleOwner, Observer {
            asteroidsAdapter.setAsteroids(it)
        })

        viewModel.imageOfTheDayLiveData.observe(viewLifecycleOwner, Observer {
            if (it.mediaType == MEDIA_TYPE_IMAGE) {
                Picasso.with(context)
                    .load(it.url)
                    .into(binding.activityMainImageOfTheDay, object : Callback {
                        override fun onSuccess() {
                            viewModel.setImageState(NetworkState.LOADED)
                        }

                        override fun onError() {
                            val failedToLoad = Constants.FAILED_TO_LOAD_IOD
                            Timber.e(failedToLoad)
                            viewModel.setImageState(NetworkState.error(failedToLoad))
                        }
                    })
            } else {
                val imageOfDayNotAvailable = getString(R.string.image_of_the_day_not_an_image)
                Timber.e(imageOfDayNotAvailable)
                viewModel.setImageState(NetworkState.error(imageOfDayNotAvailable))
            }
        })

        viewModel.asteroidsStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    binding.statusLoadingWheel.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {
                    binding.statusLoadingWheel.visibility = View.GONE
                }
                else -> {
                    binding.statusLoadingWheel.visibility = View.GONE
                    binding.tvAsteroidsState.visibility = View.VISIBLE
                }
            }
        })

        viewModel.imageStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    binding.pbImageLoading.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {
                    binding.pbImageLoading.visibility = View.GONE
                }
                else -> {
                    binding.pbImageLoading.visibility = View.GONE
                    binding.tvImageState.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
