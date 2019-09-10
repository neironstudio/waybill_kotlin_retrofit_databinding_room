package com.tetron.waybill.util

import java.util.*


class ConverterUtils {
    companion object {

        fun computeDateDiff(dateFrom: Date, dateTo: Date): Int {
            return (dateTo.time - dateFrom.time).toInt() / (60 * 1000)
        }

        fun convertHourMinToInt(hourString: String?, minString: String?): Int {


            val intHour: Int = if (hourString.isNullOrBlank()) {
                0
            } else hourString.toInt() * 60

            val intMin: Int = if (minString.isNullOrBlank()) {
                0
            } else
                minString.toInt()
            return intHour + intMin

        }

        fun hourInputCorrect(hourInputString: String?): Boolean {
            return !hourInputString.isNullOrBlank() && hourInputString.toInt() < 25

        }

        fun minutesInputCorrect(minutesInputString: String?): Boolean {
            return !minutesInputString.isNullOrBlank() && minutesInputString.toInt() < 60
        }

        fun hourAndMinutesCorrect(hourInputString: String?, minutesInputString: String?): Boolean {

            return !((!hourInputString.isNullOrBlank() && hourInputString.toInt() == 24) && !minutesInputString.isNullOrBlank() && minutesInputString.toInt() > 0)
        }
    }


}
