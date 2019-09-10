package com.tetron.waybill.util


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream


class FileUtils {

    companion object {
        fun bitmapToFile(image: Bitmap, filePath: File, compress: Int?): Boolean {

            var result = false
            try {
                val out = FileOutputStream(filePath)
                image.compress(Bitmap.CompressFormat.JPEG, compress!!, out)
                out.write(image.byteCount)
                out.flush()
                out.close()
                result = true

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result

        }

        fun fileToBitmap(filePath: String): Bitmap? {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            return BitmapFactory.decodeFile(filePath, options)
        }


    }
}