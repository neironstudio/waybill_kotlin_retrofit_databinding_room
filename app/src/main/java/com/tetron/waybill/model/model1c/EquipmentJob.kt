package com.tetron.waybill.model.model1c

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "equipment_job",
    indices = [Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = CASCADE
        )]
)

class EquipmentJob {

    @PrimaryKey
    @NonNull
    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("EquipmentTypeId")
    var equipmentTypeId: String? = null

    @SerializedName("EquipmentName")
    var equipmentName: String? = null

    @SerializedName("ExecutedTime")
    var executedTime: Int? = null

}