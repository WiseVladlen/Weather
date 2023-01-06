package com.example.weather.presentation.edit_locations

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FragmentEditLocationsBinding
import com.example.weather.domain.common.DatabaseStatus
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.views.builders.DeleteLocationDialogBuilder
import com.example.weather.utils.requireNavController
import com.example.weather.utils.navigateSafely
import com.example.weather.utils.observeOnce
import com.example.weather.utils.views.builders.NoLocationsDialogBuilder
import javax.inject.Inject

class EditLocationsFragment : Fragment(R.layout.fragment_edit_locations) {

    @Inject
    lateinit var editLocationsViewModelFactory: EditLocationsViewModel.EditLocationsViewModelFactory
    private val editLocationsViewModel by viewModels<EditLocationsViewModel> { editLocationsViewModelFactory }

    private val binding by viewBinding(FragmentEditLocationsBinding::bind)

    private var onBackPressedListener: OnBackPressedListener? = null

    private val editableLocationsAdapter = EditableLocationsAdapter { location ->
        DeleteLocationDialogBuilder.OnDeleteButtonClickListener {
            editLocationsViewModel.deleteLocation(location)
        }.let { listener ->
            DeleteLocationDialogBuilder(requireContext(), location, listener).show()
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
        setupEditableLocationsRecyclerView()
        setupOnClickListeners()
    }

    private fun setOnBackPressedListener(action: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    action()
                }
            }
        )

        onBackPressedListener = OnBackPressedListener {
            action()
        }
    }

    private fun observeDataChanges() {
        editLocationsViewModel.apply {
            initialCurrentLocationState.observeOnce(viewLifecycleOwner) { state ->
                binding.switchCurrentLocation.isChecked = state

                setupSwitchAccessCurrentLocationCheckedListener()
            }

            locationList.observe(viewLifecycleOwner) { list ->
                editableLocationsAdapter.submitList(list)
            }

            databaseStatus.distinctUntilChanged().observe(viewLifecycleOwner) { value ->
                if (value != DatabaseStatus.SELECTED_LOCATIONS_LOADED) {
                    setOnBackPressedListener {
                        NoLocationsDialogBuilder(requireContext()).show()
                    }
                } else {
                    setOnBackPressedListener {
                        requireNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setupEditableLocationsRecyclerView() {
        binding.locationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = editableLocationsAdapter
        }
    }

    private fun setupOnClickListeners() {
        with(binding) {
            editLocationsToolbar.setNavigationOnClickListener {
                onBackPressedListener?.onPress()
            }

            itemAddLocation.setOnClickListener {
                requireNavController().navigateSafely(EditLocationsFragmentDirections.actionEditLocationsFragmentToSearchLocationsFragment())
            }

            itemCurrentLocation.setOnClickListener {
                switchCurrentLocation.isChecked = !switchCurrentLocation.isChecked
            }
        }
    }

    private fun setupSwitchAccessCurrentLocationCheckedListener() {
        binding.switchCurrentLocation.setOnCheckedChangeListener { _, isChecked ->
            editLocationsViewModel.setCurrentLocationState(isChecked)
        }
    }

    private fun interface OnBackPressedListener {
        fun onPress()
    }
}