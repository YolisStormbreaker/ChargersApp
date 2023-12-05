package com.yolisstorm.chargerslist.view.chargersView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yolisstorm.chargerslist.MainActivity
import com.yolisstorm.chargerslist.R
import com.yolisstorm.chargerslist.databinding.ChargersListFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChargersListFragment : Fragment() {

    private lateinit var binding: ChargersListFragmentBinding

    private val args by navArgs<ChargersListFragmentArgs>()
    private val cityName by lazy { args.cityName }

    private val chargersListViewModel: ChargersListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChargersListFragmentBinding.inflate(inflater)

        (activity as MainActivity).updateTopBarTitle(
            resources.getString(R.string.chargers_list_screen_header)
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                chargersListViewModel.updateChargersList(cityName)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.chargersListRecyclerView) {
            adapter = ChargerListAdapter().also { adapter ->
                lifecycleScope.launch {
                    chargersListViewModel.chargersList.collect { newList ->
                        adapter.submitList(newList)
                    }
                }
            }
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}