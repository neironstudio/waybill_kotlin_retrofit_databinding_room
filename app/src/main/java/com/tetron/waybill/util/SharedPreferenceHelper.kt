package com.tetron.waybill.util

import android.content.Context
import android.content.SharedPreferences
import com.tetron.waybill.data.WaybillStep


class SharedPreferenceHelper(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("WaybillSharedPreferences", Context.MODE_PRIVATE)

    fun saveIsLogin() {
        sharedPref.edit().putBoolean(KEY_IS_LOGIN, true).apply()
    }

    fun saveLogOut() {
        sharedPref.edit().putBoolean(KEY_IS_LOGIN, false).apply()
    }

    fun getIsLoggedIn(): Boolean {
        return sharedPref.getBoolean(KEY_IS_LOGIN, false)
    }

    fun saveWaybillStep(status: WaybillStep) {
        sharedPref.edit().putString(KEY_WAYBILL_STEP, status.name).apply()
    }

    fun getWaybillStep(): String? {
        return sharedPref.getString(KEY_WAYBILL_STEP, WaybillStep.UNKNOWN.name)
    }

    fun setLocationServiceStatus(serviceStatus: Boolean) {
        sharedPref.edit().putBoolean(KEY_SERVICE_LOCATION_STATUS, serviceStatus).apply()
    }

    fun getLocationServiceStatus(): Boolean {
        return sharedPref.getBoolean(KEY_SERVICE_LOCATION_STATUS, false)
    }

    fun setFCMToken(token: String) {
        sharedPref.edit().putString(KEY_FCM_TOKEN, token).apply()
    }

    fun getFCMToken(): String? {
        return sharedPref.getString(KEY_FCM_TOKEN, "")
    }

    companion object {
        private const val KEY_IS_LOGIN = "IsLogin"
        private const val KEY_WAYBILL_STEP = "WaybillStep"
        private const val KEY_SERVICE_LOCATION_STATUS = "ServiceLocationStatus"
        private const val KEY_FCM_TOKEN = "FCMToken"
    }

}
