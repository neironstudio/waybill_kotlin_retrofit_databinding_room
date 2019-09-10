package com.tetron.waybill.model.timer

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*

@Entity
    (
    tableName = "timer",
    indices = [Index(value = ["customerDocId"])]
)
class Timer {

    @PrimaryKey
    @NonNull
    var customerDocId: String? = null

    @TypeConverters(DateConverter::class)
    var startTime: Date? = null

    var countBaseTime: Long? = null

    var countWorkTime: Long? = null

    var countLunchTime: Long? = null

    var countCustomerTime: Long? = null

    var countDriverTime: Long? = null

    var currentState: String? = null

}