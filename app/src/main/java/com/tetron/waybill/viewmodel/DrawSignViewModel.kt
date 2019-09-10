package com.tetron.waybill.viewmodel

import android.app.Application
import android.graphics.*
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.tetron.waybill.R
import com.tetron.waybill.util.FileUtils
import java.io.File

class DrawSignViewModel(app: Application) : AndroidViewModel(app) {

    fun bitmapToFile(
        bitmap: Bitmap,
        nameFile: String?,
        defaultNameSign: String


    ): Boolean {
        return if (FileUtils.bitmapToFile(bitmap, getFilePath(nameFile ?: defaultNameSign), 100)) {
            getFilePath(defaultNameSign).delete()
            false
        } else true
    }

    fun getFilePath(nameFile: String): File {

        return File(getApplication<Application>().applicationContext.filesDir, nameFile)
    }
}