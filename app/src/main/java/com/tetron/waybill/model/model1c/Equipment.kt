package com.tetron.waybill.model.model1c

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "equipment",
    indices = [Index(value = ["equipmentId"], unique = true), Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = ForeignKey.CASCADE
        )]
)

class Equipment {

    @PrimaryKey
    @NonNull
    @SerializedName("EquipmentId")
    var equipmentId: String? = null

    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("VehicleId")
    var vehicleId: String? = null

    @SerializedName("EquipmentName")
    var equipmentName: String? = null

    @SerializedName("EquipmentGroupId")
    var equipmentGroupId: String? = null

    @SerializedName("EquipmentGroupName")
    var equipmentGroupName: String? = null

}