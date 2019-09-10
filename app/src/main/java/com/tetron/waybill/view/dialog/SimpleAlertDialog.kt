package com.tetron.waybill.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.util.ConverterUtils
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.viewmodel.DialogEventViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*


open class SimpleAlertDialog(

    private val typeDialog: String,
    private val title: String,
    private val warningMsg: String?,
    private val alarmMsg: String?,
    private val param: Any?

) : DialogFragment() {
    constructor(typeDialog: String, title: String, warningMsg: String?, alarmMsg: String?) : this(
        typeDialog,
        title,
        warningMsg,
        alarmMsg,
        null
    )

    private lateinit var dialogEventViewModel: DialogEventViewModel
    lateinit var funName: Any


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        when (typeDialog) {
            TypeDialog.CONFIRMATION_MESSAGE.name -> confirmationDialog(builder, param as String)
            TypeDialog.EDIT_TEXT.name -> editTextDialogEvent(builder, null)
            TypeDialog.EDIT_TEXT_INT.name -> editTextDialogEvent(builder, param as Int)
            TypeDialog.EDIT_TIME.name -> dialogEditTime(builder)
        }

        activity?.let {
            dialogEventViewModel = ViewModelProviders.of(it)[DialogEventViewModel::class.java]
        }

        return builder.create()
    }

    @SuppressLint("InflateParams")
    private fun dialogEditTime(builder: AlertDialog.Builder) {

        val inflater: LayoutInflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_time, null)
        val tvDialogEditTimeTitle = view.findViewById<TextView>(R.id.tvDialogEditTimeTitle)
        val tvEditHours = view.findViewById<EditText>(R.id.tvEditHours)
        val tvEditMinutes = view.findViewById<EditText>(R.id.tvEditMinutes)
        val layoutAlarm = view.findViewById<LinearLayout>(R.id.layoutAlarm)
        val layoutWarnings = view.findViewById<LinearLayout>(R.id.layoutWarning)
        val tvAlarm = view.findViewById<TextView>(R.id.tvAlarm)
        val tvWarning = view.findViewById<TextView>(R.id.tvWarning)
        val btnDialogEditTimeOk = view.findViewById<Button>(R.id.btnDialogEditTimeOk)
        val btnDialogEditTimeCancel = view.findViewById<Button>(R.id.btnDialogEditTimeCancel)

        layoutWarnings.setBackgroundResource(R.color.colorPrimaryLightTransparent)
        layoutAlarm.setBackgroundResource(R.color.colorErrorTransparent)
        tvDialogEditTimeTitle.text = title
        layoutAlarm.visibility = View.GONE
        layoutWarnings.visibility = View.GONE

        tvEditHours.afterTextChanged {

            if (!ConverterUtils.hourInputCorrect(it) || (!ConverterUtils.hourAndMinutesCorrect(
                    it,
                    tvEditMinutes.text.toString()
                ))
            ) {
                showPanel(true, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)
                btnDialogEditTimeOk.isEnabled = false

            } else {
                showPanel(false, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)

                if (ConverterUtils.minutesInputCorrect(tvEditMinutes.text.toString())) {
                    btnDialogEditTimeOk.isEnabled = true
                } else showPanel(true, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)

            }

        }

        tvEditMinutes.afterTextChanged {

            if (!ConverterUtils.minutesInputCorrect(it) || (!ConverterUtils.hourAndMinutesCorrect(
                    tvEditHours.text.toString(),
                    it
                ))
            ) {
                showPanel(true, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)
                btnDialogEditTimeOk.isEnabled = false

            } else {
                showPanel(false, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)
                if (ConverterUtils.hourInputCorrect(tvEditHours.text.toString())) {
                    btnDialogEditTimeOk.isEnabled = true
                } else showPanel(true, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)

            }

        }

        btnDialogEditTimeOk.setOnClickListener {
            val callback: HashMap<String, Any> = HashMap()
            val timeList: List<String> =
                listOf(tvEditHours.text.toString(), tvEditMinutes.text.toString())

            callback[funName as String] = timeList as Any

            dialogEventViewModel.setStringEventFun(callback)

            this@SimpleAlertDialog.dialog.dismiss()
        }

        btnDialogEditTimeCancel.setOnClickListener {
            this@SimpleAlertDialog.dialog.dismiss()
        }

        builder.setView(view)
    }

    private fun editTextDialogEvent(builder: AlertDialog.Builder, typeInt: Int?) {
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_text, null)
        val titleView = view.findViewById<TextView>(R.id.tvDialogEditTextTitle)
        val tvEditTextDialog = view.findViewById<EditText>(R.id.tvEditTextDialog)
        val btnDialogEditTimeOk = view.findViewById<Button>(R.id.btnDialogEditTextOk)
        val btnDialogEditTimeCancel = view.findViewById<Button>(R.id.btnDialogEditTextCancel)
        val layoutAlarm = view.findViewById<LinearLayout>(R.id.layoutAlarm)
        val layoutWarnings = view.findViewById<LinearLayout>(R.id.layoutWarning)
        val tvAlarm = view.findViewById<TextView>(R.id.tvAlarm)
        val tvWarning = view.findViewById<TextView>(R.id.tvWarning)

        layoutAlarm.setBackgroundResource(R.color.colorErrorTransparent)
        layoutWarnings.setBackgroundResource(R.color.colorPrimaryLightTransparent)

        titleView.text = title
        layoutAlarm.visibility = View.GONE
        layoutWarnings.visibility = View.GONE

        if (typeInt != null) {
            tvEditTextDialog.inputType = InputType.TYPE_CLASS_NUMBER
            tvEditTextDialog.filters =
                arrayOf(*tvEditTextDialog.filters, InputFilter.LengthFilter(typeInt))
        } else {
            tvEditTextDialog.inputType = InputType.TYPE_CLASS_TEXT
        }

        showPanel(true, layoutAlarm, tvAlarm, layoutWarnings, tvWarning)

        btnDialogEditTimeOk.setOnClickListener {
            val callback: HashMap<String, Any> = HashMap()
            callback[funName as String] = tvEditTextDialog.text.toString() as Any
            dialogEventViewModel.setStringEventFun(callback)
            this@SimpleAlertDialog.dialog.dismiss()
        }

        btnDialogEditTimeCancel.setOnClickListener {
            this@SimpleAlertDialog.dialog.dismiss()
        }

        builder.setView(view)

    }

    private fun confirmationDialog(builder: AlertDialog.Builder, confirmationMessage: String) {
        activity?.let {
            val view = it.layoutInflater.inflate(R.layout.dialog_confirmation, waybillMainView)
            val tvDialogConfirmationTitle =
                view.findViewById<TextView>(R.id.tvDialogConfirmationTitle)
            val tvDialogConfirmationMessage =
                view.findViewById<TextView>(R.id.tvDialogConfirmationMessage)
            val btnDialogConfirmationOk = view.findViewById<Button>(R.id.btnDialogConfirmationOk)
            val btnDialogConfirmationCancel =
                view.findViewById<Button>(R.id.btnDialogConfirmationCancel)
            val layoutWarnings = view.findViewById<LinearLayout>(R.id.layoutWarning)
            val tvWarning = view.findViewById<TextView>(R.id.tvWarning)

            layoutWarnings.setBackgroundResource(R.color.colorPrimaryLightTransparent)

            tvDialogConfirmationTitle.text = title
            layoutWarnings.visibility = View.GONE
            tvDialogConfirmationMessage.text = confirmationMessage

            showPanel(true, null, null, layoutWarnings, tvWarning)

            btnDialogConfirmationOk.setOnClickListener {
                dialogEventViewModel.confirmationClickEvent()
                this@SimpleAlertDialog.dialog.dismiss()
            }

            btnDialogConfirmationCancel.setOnClickListener {
                this@SimpleAlertDialog.dialog.dismiss()
            }

            builder.setView(view)
        }

    }

    private fun showPanel(
        show: Boolean,
        linearLayoutAlarm: LinearLayout?,
        tvAlarm: TextView?,
        linearLayoutWarning: LinearLayout?,
        tvWarning: TextView?

    ) {
        if (warningMsg.isNullOrEmpty() && alarmMsg.isNullOrBlank() || !show) {
            linearLayoutAlarm?.let { it.visibility = View.GONE }
            linearLayoutWarning?.let { it.visibility = View.GONE }
        } else if (warningMsg.isNullOrEmpty()) {
            linearLayoutWarning?.let { it.visibility = View.GONE }
            linearLayoutAlarm?.let { it.visibility = View.VISIBLE }
            tvAlarm!!.text = alarmMsg
        } else if (alarmMsg.isNullOrBlank()) {
            linearLayoutAlarm?.let { it.visibility = View.GONE }
            linearLayoutWarning?.let { it.visibility = View.VISIBLE }
            tvWarning?.let { it.text = warningMsg }
        }
    }

    fun showDialog(context: Context?) {
        val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager
        this.show(fragmentManager, tag)
    }

    enum class TypeDialog {
        CONFIRMATION_MESSAGE,
        EDIT_TEXT,
        EDIT_TEXT_INT,
        EDIT_TIME
    }

}