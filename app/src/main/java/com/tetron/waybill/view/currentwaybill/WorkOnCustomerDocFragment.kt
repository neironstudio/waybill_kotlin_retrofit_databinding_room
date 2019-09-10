package com.tetron.waybill.view.currentwaybill

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.WorkOnCustomerDocFragmentBinding
import com.tetron.waybill.model.timer.Timer
import com.tetron.waybill.viewmodel.*
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*

class WorkOnCustomerDocFragment : Fragment() {

    private lateinit var viewDataBinding: WorkOnCustomerDocFragmentBinding
    private lateinit var mAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding =
            WorkOnCustomerDocFragmentBinding.inflate(inflater, container, false).apply {
                activity?.let {

                    currentWaybillViewModel =
                        ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]

                    workOnCustomerDocViewModel =
                        ViewModelProviders.of(it)[WorkOnCustomerDocViewModel::class.java]

                    trackViewModel =
                        ViewModelProviders.of(it)[TrackDataViewModel::class.java]

                    timerViewModel =
                        ViewModelProviders.of(it)[TimerViewModel::class.java]

                }

                lifecycleOwner = this@WorkOnCustomerDocFragment

            }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.clFinishWorkContainer.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

            mDialogView.tvDialogConfirmationTitle.text =
                getString(R.string.tv_dialog_finish_work_confirmation_title)
            mDialogView.tvDialogConfirmationMessage.text =
                getString(R.string.tv_dialog_finish_work_confirmation_message)

            val mBuilder = AlertDialog.Builder(context as Context)
                .setView(mDialogView)

            mAlertDialog = mBuilder.show()
            mDialogView.btnDialogConfirmationOk.setOnClickListener {
                viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
                viewDataBinding.currentWaybillViewModel?.serviceLocationStop()
                viewDataBinding.trackViewModel?.getLengthFromOsrmService()
                viewDataBinding.trackViewModel?.postWayPathWayBillServer()
                viewDataBinding.timerViewModel?.updateToStopTime()
                viewDataBinding.timerViewModel?.stopService()
                mAlertDialog.dismiss()
            }
            mDialogView.btnDialogConfirmationCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }
        viewDataBinding.timerViewModel?.getTimerData()?.observe(this, Observer {
            setFragmentElements(it)
        })
    }

    private fun setFragmentElements(timer: Timer) {

        timer.countWorkTime?.let {
            viewDataBinding.count = it
        }

    }
}