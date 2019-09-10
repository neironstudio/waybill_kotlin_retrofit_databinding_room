package com.tetron.waybill.view.currentwaybill

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.R
import com.tetron.waybill.databinding.PerformanceIndicatorsFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.PerformanceIndicatorsViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*
import kotlinx.android.synthetic.main.dialog_edit_text.view.*
import kotlinx.android.synthetic.main.dialog_edit_time.view.*
import java.util.*


class PerformanceIndicatorsFragment : Fragment() {

    private val TAG = "Performance Indicators"

    private lateinit var viewDataBinding: PerformanceIndicatorsFragmentBinding
    private lateinit var alertDialog: AlertDialog
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = PerformanceIndicatorsFragmentBinding.inflate(inflater, container, false).apply {

            performanceIndicatorsViewModel =
                ViewModelProviders.of(this@PerformanceIndicatorsFragment)[PerformanceIndicatorsViewModel::class.java]

            activity?.let {
                currentWaybillViewModel = ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
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

        viewDataBinding.clDateToStartWorkContainer.setOnClickListener {
            createDatePickerDialog(viewDataBinding.tvDateToStartWork)
        }

        viewDataBinding.clTimeToStartWorkContainer.setOnClickListener {
            createEditTimeDialog(R.string.tv_time_to_start_work_label, viewDataBinding.tvTimeToStartWork)
        }

        viewDataBinding.clEndDateContainer.setOnClickListener {
            createDatePickerDialog(viewDataBinding.tvEndDate)
        }

        viewDataBinding.clEndTimeContainer.setOnClickListener {
            createEditTimeDialog(R.string.tv_time_to_start_work_label, viewDataBinding.tvEndTime)
        }

        viewDataBinding.clRunOnWaybillContainer.setOnClickListener {
            createEditTextDialog(R.string.tv_run_on_waybill_label, viewDataBinding.tvRunOnWaybill)
        }

        viewDataBinding.btnCloseWaybill.setOnClickListener {
            createConfirmationDialog(R.string.btn_close_waybill, R.string.tv_dialog_confirm_waybill_closure_message) {
                viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Switch to Waybill closure step")
            }
        }

    }

    private fun createDatePickerDialog(
        textView: TextView
    ) {

        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)
        val currentMonth = c.get(Calendar.MONTH)
        val currentDay = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            context as Context,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->

                val formattedDay = if (day < 10) "0$day" else "$day"
                val formattedMonth = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"

                textView.text = String.Companion.format("$formattedDay.$formattedMonth.$year")

            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()

    }

    private fun createConfirmationDialog(title: Int, message: Int, onDialogOkSetListener: () -> Unit) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

        mDialogView.tvDialogConfirmationTitle.text = getString(title)
        mDialogView.tvDialogConfirmationMessage.text = getString(message)

        val mBuilder = AlertDialog.Builder(context as Context)
            .setView(mDialogView)

        alertDialog = mBuilder.show()
        mDialogView.btnDialogConfirmationOk.setOnClickListener {
            onDialogOkSetListener()
            alertDialog.dismiss()
        }
        mDialogView.btnDialogConfirmationCancel.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun createEditTextDialog(
        title: Int,
        textView: TextView,
        hint: Int? = null
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_text, waybillMainView)
        val mBuilder = AlertDialog.Builder(context as Context).setView(mDialogView)

        mDialogView.tvDialogEditTextTitle.text = getString(title)
        mDialogView.tvEditTextDialog.setText(textView.text)

        hint?.let { mDialogView.tvEditTextDialog.hint = getString(hint) }

        alertDialog = mBuilder.show()

        mDialogView.btnDialogEditTextOk.setOnClickListener {
            textView.text = mDialogView.tvEditTextDialog.text
            alertDialog.dismiss()
        }

        mDialogView.btnDialogEditTextCancel.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun createEditTimeDialog(
        title: Int,
        textView: TextView
    ) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_time, waybillMainView)
        val mBuilder = AlertDialog.Builder(context as Context).setView(mDialogView)

        mDialogView.tvDialogEditTimeTitle.text = getString(title)
        val string = textView.text.toString()
        mDialogView.tvEditHours.setText(string.substringBefore(":"))
        mDialogView.tvEditMinutes.setText(string.substringAfter(":"))


        alertDialog = mBuilder.show()

        mDialogView.btnDialogEditTimeOk.setOnClickListener {
            val hours = mDialogView.tvEditHours.text
            val minutes = mDialogView.tvEditMinutes.text
            textView.text = String.Companion.format("$hours:$minutes")
            alertDialog.dismiss()
        }

        mDialogView.btnDialogEditTimeCancel.setOnClickListener {
            alertDialog.dismiss()
        }

    }

}