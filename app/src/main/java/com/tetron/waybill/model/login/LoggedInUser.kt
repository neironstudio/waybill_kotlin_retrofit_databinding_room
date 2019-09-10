package com.tetron.waybill.model.login

/**
 * Data class that captures user information for logged in users retrieved from AccountRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)
