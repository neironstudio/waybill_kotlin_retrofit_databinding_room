package com.tetron.waybill.model.waybill

enum class PaidSubmissionLimit(val limit: Int) {
    TIME(60),
    DISTANCE(10)
}