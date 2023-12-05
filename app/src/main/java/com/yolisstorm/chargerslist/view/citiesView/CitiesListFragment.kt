package com.yolisstorm.chargerslist.view.citiesView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yolisstorm.chargerslist.MainActivity
import com.yolisstorm.chargerslist.R
import com.yolisstorm.chargerslist.databinding.CitiesListFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesListFragment : Fragment() {

    private lateinit var binding: CitiesListFragmentBinding

    private val citiesListViewModel: CitiesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CitiesListFragmentBinding
            .inflate(inflater)

        (activity as MainActivity).updateTopBarTitle(
            resources.getString(R.string.cities_list_screen_header)
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                citiesListViewModel.updateCitiesList()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.citiesListRecyclerView) {
            adapter = CitiesListAdapter(
                CityClickListener { item ->
                    view.findNavController().navigate(
                        CitiesListFragmentDirections.actionCitiesListFragmentToChargerListFragment(
                            item.cityName
                        )
                    )
                }
            ).also { adapter ->
                lifecycleScope.launch {
                    citiesListViewModel.citiesList.collect { newList ->
                        adapter.submitList(newList)
                    }
                }
            }
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}