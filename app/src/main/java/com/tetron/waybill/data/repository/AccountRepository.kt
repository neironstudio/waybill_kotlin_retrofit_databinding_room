package com.tetron.waybill.data.repository

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.db.dao.UserInfoDao
import com.tetron.waybill.data.network.api.WebService1C
import com.tetron.waybill.di.AppModuleWebService
import com.tetron.waybill.model.login.LoggedInUser
import com.tetron.waybill.model.model1c.UserCredentials
import com.tetron.waybill.model.model1c.UserInfo
import com.tetron.waybill.model.model1c.response.Response1C
import com.tetron.waybill.util.SharedPreferenceHelper
import com.tetron.waybill.util.TokenUtils
import com.tetron.waybill.util.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AccountRepository : KoinComponent {

    private val sharedPref: SharedPreferenceHelper by inject()

    private val appModuleWebService: AppModuleWebService by inject()
    private val webService1C: WebService1C = appModuleWebService.provideWebService1C()

    private val userInfoDao: UserInfoDao by inject()

    // in-memory cache of the loggedInUser object
    private var user: LoggedInUser? = null

    /*val isLoggedIn: Boolean
        get() = user != null*/

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
//        dataSource.logout()
        sharedPref.saveLogOut()
    }

    suspend fun login(username: String, password: String): Result<Response1C<UserInfo>> =
        withContext(Dispatchers.IO) {

            try {
                val response = webService1C.authUser(UserCredentials(username, password.toMD5()))

                if (!response.isSuccessful) {
                    return@withContext Result.Error(Exception(response.errorBody().toString()))
                }

                response.body()?.let {
                    if (!it.isSuccessful())
                        return@withContext Result.Error(Exception(it.getErrorMessages()))

                    it.resultData?.let { user ->
                        val id = userInfoDao.addUserInfo(user)
                        if (BuildConfig.DEBUG) {
                            Log.d(this.toString(), "id: $id")
                        }

                        setToken(user.userId)
                        sharedPref.saveIsLogin()
                        return@withContext Result.Success(it)
                    }
                }
            } catch (e: Exception) {
                Log.e(this.toString(), e.message.toString())
            }
            return@withContext Result.Error(NullPointerException())
        }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun isLogged(): Boolean = sharedPref.getIsLoggedIn()

    private fun setToken(userId: String?) {
        if (sharedPref.getFCMToken().isNullOrEmpty()) {
            FirebaseInstanceId.getInstance()
                .instanceId.addOnCompleteListener(OnCompleteListener {
                if (!it.isSuccessful) {
                    Log.w(this.toString(), "getInstanceId failed", it.exception)
                    return@OnCompleteListener
                }

                val token = it.result?.token
                if (!token.isNullOrEmpty()) {
                    val tokenUtils = TokenUtils()
                    tokenUtils.setToken(token, userId)
                }
            })
        }
    }
}
