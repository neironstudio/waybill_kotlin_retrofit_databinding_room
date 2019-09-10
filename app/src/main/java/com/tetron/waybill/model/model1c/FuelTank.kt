package com.tetron.waybill.model.model1c

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "fuel_tank",
    indices = [Index(value = ["fuelTankId"], unique = true), Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = ForeignKey.CASCADE
        )]
)

class FuelTank {

    @PrimaryKey
    @NonNull
    @SerializedName("FuelTankId")
    var fuelTankId: Int? = null

    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("VehicleId")
    var vehicleId: String? = null

    @SerializedName("EquipmentTypeId")
    var equipmentTypeId: String? = null

    @SerializedName("FuelTypeId")
    var fuelTypeId: String? = null

    @SerializedName("FuelTypeName")
    var fuelTypeName: String? = null

    @SerializedName("FuelGroupId")
    var fuelGroupId: String? = null

    @SerializedName("FuelGroupName")
    var fuelGroupName: String? = null

    @SerializedName("StartRemain")
    var startRemain: Float? = null

    @SerializedName("EndRemain")
    var endRemain: Float? = null

    @SerializedName("HourCounter")
    var hourCounter: Int? = null

    @SerializedName("FuelNorm")
    var fuelNorm: Float? = null

    @SerializedName("FuelFact")
    var fuelFact: Float? = null

    @SerializedName("IsMainTank")
    var isMainTank: Boolean? = null

}