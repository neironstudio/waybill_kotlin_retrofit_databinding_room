package com.tetron.waybill.model.model1c

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*

@Entity(
    tableName = "refueling",
    indices = [Index(value = ["fuelTankId"], unique = true), Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = ForeignKey.CASCADE
        )]
)

class Refueling {

    @PrimaryKey
    @NonNull
    @SerializedName("FuelTankId")
    var fuelTankId: Int? = null

    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("Date")
    @TypeConverters(DateConverter::class)
    var date: Date? = null

    @SerializedName("FuelTypeId")
    var fuelTypeId: String? = null

    @SerializedName("FuelingType")
    var fuelingType: String? = null

    @SerializedName("FuelCardId")
    var fuelCardId: String? = null

    @SerializedName("FuelCardNumber")
    var fuelCardNumber: String? = null

    @SerializedName("Filled")
    var filled: Int? = null

    @SerializedName("Dropped")
    var dropped: Int? = null
}