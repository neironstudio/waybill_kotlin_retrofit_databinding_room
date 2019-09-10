package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.TimeSpentFragmentBinding
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.view.dialog.DialogConfirmationMessage
import com.tetron.waybill.viewmodel.CurrentCustomerDocViewModel
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.DialogEventViewModel
import com.tetron.waybill.viewmodel.TimeSpentViewModel

class TimeSpentFragment : Fragment() {

    private lateinit var viewDataBinding: TimeSpentFragmentBinding
    private lateinit var dialogEventViewModel: DialogEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = TimeSpentFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {
                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
                dialogEventViewModel = ViewModelProviders.of(it)[DialogEventViewModel::class.java]
            }

            currentCustomerDocViewModel =
                ViewModelProviders.of(this@TimeSpentFragment)[CurrentCustomerDocViewModel::class.java]
            timeSpentViewModel =
                ViewModelProviders.of(this@TimeSpentFragment)[TimeSpentViewModel::class.java]

            lifecycleOwner = this@TimeSpentFragment
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialogEventViewModel.confirmationEvent.observe(this@TimeSpentFragment, Observer {
            viewDataBinding.currentCustomerDocViewModel?.updateTimeSpent()
            viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
        })

        viewDataBinding.apply {

            btnConfirm.setOnClickListener {
                val dialogConfirm = DialogConfirmationMessage(
                    getString(R.string.tv_time_spent_title),
                    getString(R.string.tv_time_spent_confirm_message)
                )
                dialogConfirm.showDialog(context)
            }

            etTimeToCustomer.afterTextChanged {
                currentCustomerDocViewModel?.timeToCustomerChanged(etTimeToCustomer.text.toString())
            }

            etDistanceToCustomer.afterTextChanged {
                currentCustomerDocViewModel?.distanceToCustomerChanged(etDistanceToCustomer.text.toString())
            }


            etTimeToBase.afterTextChanged {
                currentCustomerDocViewModel?.timeToBaseChanged(etTimeToBase.text.toString())
            }


            etDistanceToBase.afterTextChanged {
                currentCustomerDocViewModel?.distanceToBaseChanged(etDistanceToBase.text.toString())
            }

        }

    }


}