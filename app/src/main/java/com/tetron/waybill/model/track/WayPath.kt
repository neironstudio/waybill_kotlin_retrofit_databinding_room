package com.tetron.waybill.model.track

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*

@Entity
    (tableName = "waypath", indices = [Index(value = ["customerDocId", "wayType"], unique = true)])
class WayPath {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var customerDocId: String? = null

    @TypeConverters(DateConverter::class)
    var startTime: Date? = null

    @TypeConverters(DateConverter::class)
    var finishTime: Date? = null

    var length: Int? = null

    var activ: Boolean? = null

    var htmlStringOpenStreet: String? = null

    var wayType: String? = null

}