package com.tetron.waybill.model.model1c


import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*


@Entity(
    tableName = "customer_doc",
    indices = [Index(value = ["id"], unique = true), Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = CASCADE
        )]
)

class CustomerDoc {

    @PrimaryKey
    @NonNull
    @SerializedName("CustomerDocId")
    var id: String? = null

    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("Date")
    @TypeConverters(DateConverter::class)
    var date: Date? = null

    @SerializedName("Number")
    var number: String? = null

    @SerializedName("CustomerId")
    var customerId: String? = null

    @SerializedName("CustomerName")
    var customerName: String? = null

    @SerializedName("CustomerDeptId")
    var customerDeptId: String? = null

    @SerializedName("CustomerDeptName")
    var customerDeptName: String? = null

    @SerializedName("ExecutedTime")
    var executedTime: Int? = null

    @SerializedName("ExecutedEquipmentTime")
    var executedEquipmentTime: Int? = null

    @SerializedName("ExecutedDistance")
    var executedDistance: Int? = null

    @SerializedName("OdometerStart")
    var odometerStart: Int? = null

    @SerializedName("OdometerFinish")
    var odometerFinish: Int? = null

    @SerializedName("StartDate")
    @TypeConverters(DateConverter::class)
    var startDate: Date? = null

    @SerializedName("EndDate")
    @TypeConverters(DateConverter::class)
    var endDate: Date? = null

    @SerializedName("IsNonCommercial")
    var isNonCommercial: Boolean? = null

    @SerializedName("ArrivalAddress")
    var arrivalAddress: String? = null

    @SerializedName("Purpose")
    var purpose: String? = null

    @SerializedName("ContactPerson")
    var contactPerson: String? = null

    var contactPersonPosition: String? = null

    @SerializedName("ContactPhone")
    var contactPhone: String? = null

    var trailerUsed: Boolean? = null

    var timeToCustomer: Int? = 0

    var distanceToCustomer: Int? = 0

    var timeToBase: Int? = 0

    var distanceToBase: Int? = 0

}