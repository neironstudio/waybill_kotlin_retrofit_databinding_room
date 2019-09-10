package com.tetron.waybill.util

import com.tetron.waybill.model.waybill.PaidSubmissionLimit

class SubmissionCheck {

    companion object {

        @JvmSynthetic
        private val paidTimeLimit = PaidSubmissionLimit.TIME.limit
        @JvmSynthetic
        private val paidDistanceLimit = PaidSubmissionLimit.DISTANCE.limit

        fun checkSubmissionDistanceToCustomer(distanceToCustomer: Int): Int {
            var checkedDistanceToCustomer = distanceToCustomer
            if (distanceToCustomer > paidDistanceLimit) {
                checkedDistanceToCustomer = paidDistanceLimit
            } else if (distanceToCustomer < 0) {
                checkedDistanceToCustomer = 0
            }
            return checkedDistanceToCustomer
        }

        fun checkTimeToCustomer(timeToCustomer: Int): Int {
            var checkedTimeToCustomer = timeToCustomer
            if (timeToCustomer > paidTimeLimit) {
                checkedTimeToCustomer = paidTimeLimit
            } else if (timeToCustomer < 0) {
                checkedTimeToCustomer = 0
            }
            return checkedTimeToCustomer
        }


    }
}