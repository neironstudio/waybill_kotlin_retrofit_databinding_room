package com.tetron.waybill.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.repository.AccountRepository
import com.tetron.waybill.model.login.LoginFormState
import com.tetron.waybill.model.model1c.UserInfo
import kotlinx.coroutines.launch


class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val accountRepository = AccountRepository()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginSuccess = MutableLiveData<UserInfo>()
    val loginSuccess: LiveData<UserInfo> = _loginSuccess

    private val _loginFailed = MutableLiveData<String>()
    val loginFailed: LiveData<String> = _loginFailed

    fun login(username: String, password: String) {
        Log.d(this.toString(), "login")
        _loginForm.value = LoginFormState(isChecking = true)

        viewModelScope.launch {
            val result = accountRepository.login(username, password)

            if (result is Result.Success) {
                _loginSuccess.value = result.data.resultData
            }

            if (result is Result.Error) {
                _loginFailed.value = result.exception.message.toString()
            }

            _loginForm.value = LoginFormState(isChecking = false)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.err_invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.err_invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length > 1
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 3
    }

    fun checkIsLoggedIn(): Boolean = accountRepository.isLogged()
}