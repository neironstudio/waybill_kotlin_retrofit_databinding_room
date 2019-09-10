package com.tetron.waybill.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment

class PermissionUtils {

    companion object {


        fun hasPermission(activity: Activity, permission: String): Boolean {

            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermissions(activity: Activity, permission: Array<String>, requestCode: Int) {

            return activity.requestPermissions(permission, requestCode)
        }

        fun requestPermissions(fragment: Fragment, permission: Array<String>, requestCode: Int) {

            return fragment.requestPermissions(permission, requestCode)
        }

        fun shouldShowRational(activity: Activity, permission: String): Boolean {

            return activity.shouldShowRequestPermissionRationale(permission)
        }


        fun goToAppSettings(activity: Activity) {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.packageName, null)
            )
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }

        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetwork
            return netInfo != null
        }


    }
}