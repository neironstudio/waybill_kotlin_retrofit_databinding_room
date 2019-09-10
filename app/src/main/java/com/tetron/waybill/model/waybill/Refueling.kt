package com.tetron.waybill.model.waybill

data class Refueling(
    val id: Int,
    val date: String,
    val time: String,
    val address: String,
    val amount: Int
)