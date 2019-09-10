package com.tetron.waybill.model.model1c


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "fuel_card",
    indices = [Index(value = ["fuelCardId"], unique = true), Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = ForeignKey.CASCADE
        )]
)

class FuelCard {

    @PrimaryKey
    @NonNull
    @SerializedName("FuelCardId")
    var fuelCardId: String? = null

    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("VehicleId")
    var vehicleId: String? = null

    @SerializedName("FuelCardNumber")
    var fuelCardNumber: String? = null

    @SerializedName("FuelTypeId")
    var fuelTypeId: String? = null

    @SerializedName("FuelTypeName")
    var fuelTypeName: String? = null

    @SerializedName("FuelGroupId")
    var fuelGroupId: String? = null

    @SerializedName("FuelGroupName")
    var fuelGroupName: String? = null

}