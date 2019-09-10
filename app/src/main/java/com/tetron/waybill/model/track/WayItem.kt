package com.tetron.waybill.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WayItem {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var wayPathId: Int? = null

    var lan: Double? = null

    var lon: Double? = null

    var accuracy: Double? = null

    var status: String? = null

}