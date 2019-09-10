package com.tetron.waybill.util

import android.util.Log
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.data.db.dao.UserInfoDao
import com.tetron.waybill.data.network.api.WebService1C
import com.tetron.waybill.di.AppModuleWebService
import com.tetron.waybill.model.model1c.DeviceToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class TokenUtils : KoinComponent {

    private val sharedPref: SharedPreferenceHelper by inject()

    private val appModuleWebService: AppModuleWebService by inject()
    private val webService1C: WebService1C = appModuleWebService.provideWebService1C()

    private val userInfoDao: UserInfoDao by inject()

    fun setToken(token: String, _userId: String? = null) {

        if (BuildConfig.DEBUG) {
            Log.d(this.toString(), "New token received: $token")
        }

        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until 10) {
                val userId = _userId ?: userInfoDao.getUserId()

                // if clean data, new token received before login and we go to boot loop
                // otherwise userId cannot be null
                if (userId.isNullOrBlank()) return@launch

                val oldToken = sharedPref.getFCMToken() ?: ""

                try {
                    val response = webService1C.deviceToken(DeviceToken(userId, token, oldToken))

                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.isSuccessful()) {
                                sharedPref.setFCMToken(token)
                                return@launch
                            }
                        }
                    }
                    Unit
                } catch (e: Exception) {
                    Log.e(this.toString(), e.message.toString())
                }
                delay(5 * SEC)
            }
        }
    }
}
