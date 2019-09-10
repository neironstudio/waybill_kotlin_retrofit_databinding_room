package com.tetron.waybill.view.currentwaybill

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.databinding.WaybillClosureFragmentBinding
import com.tetron.waybill.view.subsystemselection.SubsystemSelectionActivity
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.TimerViewModel

class WaybillClosureFragment : Fragment() {

    private val TAG = "WaybillClosure"

    private lateinit var viewDataBinding: WaybillClosureFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = WaybillClosureFragmentBinding.inflate(inflater, container, false).apply {
            activity?.let {
                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
                timerViewModel = ViewModelProviders.of(it)[TimerViewModel::class.java]
            }
        }

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialogs()
    }

    private fun setupDialogs() {
        viewDataBinding.btnGoToSubsystemSelection.setOnClickListener {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Go to subsystem selection")
            }
            startSubsystemSelectionActivity()

            //Added for test's help
            viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
            viewDataBinding.currentWaybillViewModel?.resetApprovals()
            viewDataBinding.timerViewModel?.clearTimer()
        }
    }

    private fun startSubsystemSelectionActivity() {
        val intent = Intent(activity, SubsystemSelectionActivity::class.java)
        startActivity(intent)
    }

}