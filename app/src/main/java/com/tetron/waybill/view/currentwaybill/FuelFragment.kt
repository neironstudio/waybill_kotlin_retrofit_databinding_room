package com.tetron.waybill.view.currentwaybill

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetron.waybill.R
import com.tetron.waybill.adapters.RefuelingsListAdapter
import com.tetron.waybill.databinding.FuelFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.FuelViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*

class FuelFragment : Fragment() {

    private lateinit var viewDataBinding: FuelFragmentBinding

    private lateinit var listAdapter: RefuelingsListAdapter

    private lateinit var mAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FuelFragmentBinding.inflate(inflater, container, false).apply {
            currentWaybillViewModel = ViewModelProviders.of(activity!!)[CurrentWaybillViewModel::class.java]
            fuelViewModel = ViewModelProviders.of(this@FuelFragment)[FuelViewModel::class.java]
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()

        viewDataBinding.btnConfirmFuelConsumption.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

            mDialogView.tvDialogConfirmationTitle.text = getString(R.string.tv_dialog_confirm_fuel_consumption_title)
            mDialogView.tvDialogConfirmationMessage.text =
                getString(R.string.tv_dialog_confirm_fuel_consumption_message)

            val mBuilder = AlertDialog.Builder(context as Context)
                .setView(mDialogView)

            mAlertDialog = mBuilder.show()
            mDialogView.btnDialogConfirmationOk.setOnClickListener {
                viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
                mAlertDialog.dismiss()
            }
            mDialogView.btnDialogConfirmationCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.fuelViewModel ?: return
        viewModel.apply {
            listAdapter = RefuelingsListAdapter(viewModel)
            viewDataBinding.rvRefuelings.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    DividerItemDecoration(
                        context, LinearLayoutManager.VERTICAL
                    )
                )
                setHasFixedSize(true)
            }
        }
    }

}