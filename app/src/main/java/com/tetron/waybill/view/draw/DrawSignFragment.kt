package com.tetron.waybill.view.draw


import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.FragmentDrawSignBinding
import com.tetron.waybill.util.FileUtils
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.DrawSignViewModel
import com.tetron.waybill.viewmodel.SimpleSwitchFragmentEventViewModel
import kotlinx.android.synthetic.main.activity_current_waybill.*
import kotlinx.android.synthetic.main.dialog_confirmation.view.*
import java.io.File


class DrawSignFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentDrawSignBinding
    private lateinit var mAlertDialog: AlertDialog
    private val defaultNameSign: String = "signDefault.jpg"
    private var widthPixels: Int = 0
    private var heightPixel: Int = 0

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentDrawSignBinding.inflate(inflater, container, false).apply {

            activity?.let {
                drawSignViewModel =
                    ViewModelProviders.of(it)[DrawSignViewModel::class.java]
                simpleSwitchFragmentEventViewModel =
                    ViewModelProviders.of(it)[SimpleSwitchFragmentEventViewModel::class.java]
                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]

            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setBitMapParam()
        viewDataBinding.btnClear.setOnClickListener {

            viewDataBinding.drawing.clearCanvas()
        }
        viewDataBinding.btnClose.setOnClickListener {

            viewDataBinding.simpleSwitchFragmentEventViewModel?.setCustomerMarkFragment()

        }
        viewDataBinding.btnConfirm.setOnClickListener {

            createDialog(
                getString(R.string.title_dialog_sign_apply),
                getString(R.string.tv_sign_right),
                getString(R.string.btn_confirm),
                getString(R.string.btn_cancel)
            )
        }

    }

    private fun setBitMapParam() {

        widthPixels = resources.displayMetrics.widthPixels
        heightPixel = resources.displayMetrics.heightPixels
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

        super.onConfigurationChanged(newConfig)
        viewDataBinding.landscape = configurationLandscape()


    }

    override fun onPause() {

        super.onPause()
        saveBitmap(null)
    }

    private fun configurationLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    }

    override fun onResume() {
        super.onResume()
        val bitmap =

            viewDataBinding.drawSignViewModel?.getFilePath(defaultNameSign)?.canonicalPath?.let {
                FileUtils.fileToBitmap(
                    it
                )
            }
        bitmap?.let {
            widthPixels = it.width
            heightPixel = it.height
            viewDataBinding.drawing.setBitmap(it)
        }

        viewDataBinding.landscape = configurationLandscape()

    }

    private fun createDialog(title: String, msg: String, ok: String, no: String) {

        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, waybillMainView)

        mDialogView.tvDialogConfirmationTitle.text = title
        mDialogView.tvDialogConfirmationMessage.text = msg
        mDialogView.btnDialogConfirmationCancel.text = no
        mDialogView.btnDialogConfirmationOk.text = ok

        val mBuilder = AlertDialog.Builder(context as Context)
            .setView(mDialogView)

        mAlertDialog = mBuilder.show()

        mDialogView.btnDialogConfirmationOk.setOnClickListener {
            //TODO Когда будет сделано чтение идентификатора - сформировать на его основе имя файла
            saveBitmap("newFile.jpg")
            viewDataBinding.currentWaybillViewModel?.switchWaybillStep()
            mAlertDialog.dismiss()

        }
        mDialogView.btnDialogConfirmationCancel.setOnClickListener {

            mAlertDialog.dismiss()
        }
    }

    private fun saveBitmap(nameFile: String?) {

        widthPixels = viewDataBinding.drawing.width
        heightPixel = viewDataBinding.drawing.height

        val bitmap = Bitmap.createBitmap(widthPixels, heightPixel, Bitmap.Config.ARGB_8888)
        val well = viewDataBinding.drawing.getBitmap(widthPixels, heightPixel)
        val paint = Paint()
        paint.color = Color.WHITE
        val now = Canvas(bitmap)
        now.drawRect(Rect(0, 0, widthPixels, heightPixel), paint)
        now.drawBitmap(

            well,
            Rect(0, 0, well.width, well.height),
            Rect(0, 0, widthPixels, heightPixel),
            null
        )

        viewDataBinding.drawSignViewModel?.bitmapToFile(bitmap, nameFile, defaultNameSign).let {
            if (it == true) {
                Toast.makeText(
                    context,
                    getString(R.string.error_creating_file_sign),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

}
