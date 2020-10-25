package com.arya199.gemstone.rate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arya199.gemstone.PreferenceHelper
import com.arya199.gemstone.PreferenceHelper.lastLiveUpdate
import com.arya199.gemstone.PreferenceHelper.shouldLiveUpdate
import com.arya199.gemstone.databinding.RateGridFragBinding
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RateGridFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val sharedViewModel by activityViewModels<RateViewModel> {
        viewModelFactory
    }

    private lateinit var viewDataBinding : RateGridFragBinding

    private lateinit var listAdapter: RateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = RateGridFragBinding.inflate(inflater, container, false)
            .apply {
                rateViewModel = sharedViewModel
                fragmentHandler = RateGridFragmentHandler()
            }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        val prefHelper = PreferenceHelper.customPreference(this.requireContext())
        val prefHelperShouldUpdate = prefHelper.shouldLiveUpdate()
        sharedViewModel.loadRates(prefHelperShouldUpdate) {
            if (prefHelperShouldUpdate) prefHelper.lastLiveUpdate = it
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm")
            val netDate = Date(prefHelper.lastLiveUpdate)
            viewDataBinding.liveLastUpdateText.text = "Last update: ${sdf.format(netDate)}"
        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.rateViewModel
        if (viewModel != null) {
            listAdapter = RateAdapter()
            viewDataBinding.rateList.adapter = listAdapter
        }
    }

    inner class RateGridFragmentHandler {

        fun onRefresh() {
            val viewModel = viewDataBinding.rateViewModel
            val prefHelper = PreferenceHelper.customPreference(viewDataBinding.root.context)
            if (viewModel != null) {
                sharedViewModel.loadRates(true) {
                    prefHelper.lastLiveUpdate = it
                    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm")
                    val netDate = Date(prefHelper.lastLiveUpdate)
                    viewDataBinding.liveLastUpdateText.text = "Last update: ${sdf.format(netDate)}"
                }
            }
        }
    }
}