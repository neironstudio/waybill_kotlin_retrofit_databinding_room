package com.tetron.waybill.model.login

import com.tetron.waybill.model.model1c.UserInfo

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: UserInfo? = null,
    val error: Int? = null
)
