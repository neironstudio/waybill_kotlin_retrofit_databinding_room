package com.tetron.waybill.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.databinding.LoginActivityBinding
import com.tetron.waybill.model.model1c.UserInfo
import com.tetron.waybill.util.PermissionUtils
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.util.toast
import com.tetron.waybill.view.dialog.DialogConfirmationMessage
import com.tetron.waybill.view.subsystemselection.SubsystemSelectionActivity
import com.tetron.waybill.viewmodel.LoginViewModel
import com.tetron.waybill.viewmodel.OrderDataViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var orderDataViewModel: OrderDataViewModel
    private lateinit var viewDataBinding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]
        viewDataBinding.loginViewModel = loginViewModel
        viewDataBinding.lifecycleOwner = this

        orderDataViewModel =
            ViewModelProviders.of(this).get(OrderDataViewModel::class.java)

        if (!PermissionUtils.isOnline(this)) {
            val dialogConfirmationMessage = DialogConfirmationMessage(
                getString(R.string.internet_alarm_title), getString(
                    R.string.internet_alarm_message
                )
            )
            dialogConfirmationMessage.showDialog(this)
        }

        if (loginViewModel.checkIsLoggedIn()) {
            if (PermissionUtils.isOnline(this)) {
                orderDataViewModel.getOrderResponse()  // test Load From 1C
            }
            startSubsystemSelectionActivity()
            finish()
        } else {
            initializationLoginActivity()
        }
    }

    private fun initializationLoginActivity() {
        Log.d(this.toString(), "initializationLoginActivity")

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            if (loginState.usernameError != null) {
                tilUsername.error = getString(loginState.usernameError)
            } else {
                tilUsername.error = null
            }
            if (loginState.passwordError != null) {
                tilPassword.error = getString(loginState.passwordError)
            } else {
                tilPassword.error = null
            }
        })

        loginViewModel.loginSuccess.observe(this@LoginActivity, Observer {
            val loginSuccess = it ?: return@Observer

            if (PermissionUtils.isOnline(this)) {
                orderDataViewModel.getOrderResponse()  // test Load From 1C
            }
            updateUiWithUser(loginSuccess)
            startSubsystemSelectionActivity()

            finish()
        })

        loginViewModel.loginFailed.observe(this@LoginActivity, Observer {
            val loginFailed = it ?: return@Observer

            showLoginFailed(loginFailed)
        })

        etUsername.afterTextChanged {
            loginViewModel.loginDataChanged(
                etUsername.text.toString(),
                etPassword.text.toString()
            )
        }

        etPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    etUsername.text.toString(),
                    etPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        if (btnLogin.isEnabled) {
                            btnLogin.callOnClick()
                        }
                }
                false
            }

        }
    }

    private fun updateUiWithUser(info: UserInfo) {
        val welcome = getString(R.string.msg_welcome)
        val displayName = info.userName

        toast("$welcome $displayName")
    }

    private fun showLoginFailed(errorString: String) {
        val text = getString(R.string.err_login_failed) + ". " + errorString
        Log.d(this.toString(), text)
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun startSubsystemSelectionActivity() {
        val intent = Intent(this, SubsystemSelectionActivity::class.java)
        startActivity(intent)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */

