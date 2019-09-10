package com.tetron.waybill.view.currentwaybill

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.DepartureToCustomerFragmentBinding
import com.tetron.waybill.util.PermissionUtils
import com.tetron.waybill.viewmodel.CurrentCustomerDocStatusViewModel
import com.tetron.waybill.viewmodel.CurrentCustomerDocViewModel
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.TimerViewModel

class DepartureToCustomerFragment : Fragment() {
    private val permissionFineLocation: String = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionCoarseLocation: String = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var viewDataBinding: DepartureToCustomerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DepartureToCustomerFragmentBinding.inflate(inflater, container, false).apply {
                activity?.let {
                    currentWaybillViewModel =
                        ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]

                    currentCustomerDocViewModel =
                        ViewModelProviders.of(it)[CurrentCustomerDocViewModel::class.java]

                    currentCustomerDocStatusViewModel =
                        ViewModelProviders.of(it)[CurrentCustomerDocStatusViewModel::class.java]

                    timerViewModel =
                        ViewModelProviders.of(it)[TimerViewModel::class.java]

                    initRequestPermission()
                    lifecycleOwner = this@DepartureToCustomerFragment
                }
            }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.currentWaybillViewModel?.errorCallBack?.observe(activity!!, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            // dialog returns
            PermissionUtils.goToAppSettings(activity!!)


        })
    }

    override fun onResume() {

        if (getPermission(permissionFineLocation)) viewDataBinding.currentWaybillViewModel?.setPermissionLocation(
            true
        ) //after set system settings

        super.onResume()

    }

    private fun getPermission(permissionName: String): Boolean {
        return PermissionUtils.hasPermission(activity!!, permissionName)
    }

    private fun initRequestPermission() {

        PermissionUtils.requestPermissions(
            this,
            arrayOf(permissionFineLocation, permissionCoarseLocation),
            1
        )

    }
}