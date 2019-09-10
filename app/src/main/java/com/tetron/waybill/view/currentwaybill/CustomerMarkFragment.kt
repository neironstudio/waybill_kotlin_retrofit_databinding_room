package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.CustomerMarkFragmentBinding
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.CustomerMarkViewModel
import com.tetron.waybill.viewmodel.SimpleSwitchFragmentEventViewModel


class CustomerMarkFragment : Fragment() {

    private lateinit var viewDataBinding: CustomerMarkFragmentBinding

    private lateinit var simpleSwitchFragmentEventViewModel: SimpleSwitchFragmentEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = CustomerMarkFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {

                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]

                simpleSwitchFragmentEventViewModel =
                    ViewModelProviders.of(it)[SimpleSwitchFragmentEventViewModel::class.java]

            }

            customerMarkViewModel =
                ViewModelProviders.of(this@CustomerMarkFragment)[CustomerMarkViewModel::class.java]

            lifecycleOwner = this@CustomerMarkFragment

        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.apply {

            btnSubscribe.setOnClickListener {
                simpleSwitchFragmentEventViewModel.setSignDrawFragment()
            }

            etContactPerson.afterTextChanged {
                customerMarkViewModel?.validateContactPersonWithNotifyObservers(
                    etContactPerson.text.toString()
                )
            }

            etContactPersonPosition.afterTextChanged {
                customerMarkViewModel?.validateContactPersonPositionWithNotifyObservers(
                    etContactPersonPosition.text.toString()
                )
            }

            etOdometerArrival.afterTextChanged {
                customerMarkViewModel?.validateOdometersWithNotifyObservers(
                    etOdometerArrival.text.toString().toIntOrNull(),
                    etOdometerDeparture.text.toString().toIntOrNull()
                )
            }

            etOdometerDeparture.afterTextChanged {
                customerMarkViewModel?.validateOdometersWithNotifyObservers(
                    etOdometerArrival.text.toString().toIntOrNull(),
                    etOdometerDeparture.text.toString().toIntOrNull()
                )
            }

            etMachineKilometers.afterTextChanged {
                customerMarkViewModel?.validateMachineKilometersWithNotifyObservers(
                    etMachineKilometers.text.toString().toIntOrNull()
                )
            }

            etTravelTime.afterTextChanged {
                customerMarkViewModel?.validateTravelTimeWithNotifyObservers(
                    etTravelTime.text.toString()
                )
            }

            etAdditionalEquipmentWorkingTime.afterTextChanged {
                customerMarkViewModel?.validateAdditionalEquipmentWorkingTimeWithNotifyObservers(
                    etAdditionalEquipmentWorkingTime.text.toString()
                )
            }

        }

    }

}