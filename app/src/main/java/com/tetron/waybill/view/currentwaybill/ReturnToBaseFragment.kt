package com.tetron.waybill.view.currentwaybill

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.ReturnToBaseFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*


class ReturnToBaseFragment : Fragment() {

    private lateinit var viewDataBinding: ReturnToBaseFragmentBinding

    private lateinit var mAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ReturnToBaseFragmentBinding.inflate(inflater, container, false).apply {
            currentWaybillViewModel = ViewModelProviders.of(activity!!).get(CurrentWaybillViewModel::class.java)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnReturnedToBase.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

            mDialogView.tvDialogConfirmationTitle.text = getString(R.string.btn_returned_to_base)
            mDialogView.tvDialogConfirmationMessage.text =
                getString(R.string.tv_dialog_returned_to_base_confirmation_message)

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
}