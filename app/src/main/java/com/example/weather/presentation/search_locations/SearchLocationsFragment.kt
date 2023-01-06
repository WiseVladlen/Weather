package com.example.weather.presentation.search_locations

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchLocationsBinding
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.navigateSafely
import com.example.weather.utils.requireNavController
import com.example.weather.utils.views.hideSoftKeyboard
import com.example.weather.utils.views.onQueryTextChanged
import com.example.weather.utils.views.showSoftKeyboard
import javax.inject.Inject

class SearchLocationsFragment : Fragment(R.layout.fragment_search_locations) {

    @Inject
    lateinit var searchLocationsViewModelFactory: SearchLocationsViewModel.SearchLocationsFragmentFactory
    private val searchLocationsViewModel by viewModels<SearchLocationsViewModel> { searchLocationsViewModelFactory }

    private val binding by viewBinding(FragmentSearchLocationsBinding::bind)

    private val searchedLocationsAdapter = SearchedLocationsAdapter { location ->
        requireView().hideSoftKeyboard()

        searchLocationsViewModel.addLocation(location) {
            requireNavController().navigateSafely(SearchLocationsFragmentDirections.actionSearchLocationsFragmentToMainFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        observeDataChanges()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        WeatherApplication.INSTANCE.appComponent?.inject(this)
    }

    private fun initializeViews() {
        setupSearchedLocationsRecyclerView()
        setupActionBar()
        requireView().showSoftKeyboard()
    }

    private fun observeDataChanges() {
        searchLocationsViewModel.apply {
            recyclerViewVisibility.observe(viewLifecycleOwner) { visibility ->
                binding.searchedLocationsRecyclerView.visibility = visibility
            }

            searchResultMessage.observe(viewLifecycleOwner) { message ->
                binding.textViewMessage.apply {
                    text = message.title
                    visibility = message.visibility
                }
            }

            filteredLocationList.observe(viewLifecycleOwner) { list ->
                searchedLocationsAdapter.submitList(list)
            }
        }
    }

    private fun setupActionBar() {
        with(binding) {
            searchLocationsToolbar.setNavigationOnClickListener {
                requireView().hideSoftKeyboard()
                requireNavController().navigateUp()
            }

            searchView.onQueryTextChanged { query ->
                searchLocationsViewModel.searchQuery.value = query
            }
        }
    }

    private fun setupSearchedLocationsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            linearLayoutManager.orientation,
        )

        binding.searchedLocationsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = searchedLocationsAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}