package com.tetron.waybill.model.waybill

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tetron.waybill.model.model1c.WaybillDoc

@Entity(
    tableName = "current_waybill_status",
    indices = [Index(value = ["waybillId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = ForeignKey.CASCADE
        )]
)

data class CurrentWaybillStatus(

    @PrimaryKey
    val waybillId: String,

    val medicalApprove: Boolean = false,

    val technicalApprove: Boolean = false

)