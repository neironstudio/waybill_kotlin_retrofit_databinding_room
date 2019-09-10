package com.tetron.waybill.view.currentwaybill

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.PrintFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.PrintViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*

class PrintWaybillFragment : Fragment() {

    private lateinit var viewDataBinding: PrintFragmentBinding

    private lateinit var mAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = PrintFragmentBinding.inflate(inflater, container, false).apply {
            activity?.let {
                currentWaybillViewModel =
                    ViewModelProviders.of(it).get(CurrentWaybillViewModel::class.java)
                printViewModel = ViewModelProviders.of(it).get(PrintViewModel::class.java)

            }
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.printWaybillFragment = this
        viewDataBinding.printViewModel?.testPrintEnable = false
        viewDataBinding.progressBarVisibility = false
        viewDataBinding.printViewModel?.statusPrint?.observe(this@PrintWaybillFragment, Observer {
            if (it is String) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewDataBinding.progressBarVisibility = false
            }
            if (it is Boolean) {
                viewDataBinding.progressBarVisibility = false
                createDialog()
            }

        })

    }

    fun setProgressBar(): Boolean {
        viewDataBinding.progressBarVisibility = true
        return true
    }

    fun createDialog(): Boolean {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

        mDialogView.tvDialogConfirmationTitle.text =
            getString(R.string.tv_dialog_print_confirmation_title)
        mDialogView.tvDialogConfirmationMessage.text =
            getString(R.string.tv_dialog_print_confirmation_message)
        mDialogView.btnDialogConfirmationCancel.text = getString(R.string.btn_no)
        mDialogView.btnDialogConfirmationOk.text = getString(R.string.btn_yes)

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
        return true
    }

}