package com.tetron.waybill.model.model1c

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*

@Entity(tableName = "waybill_doc", indices = [Index(value = ["waybillId"], unique = true)])

class WaybillDoc(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("WaybillId")
    var waybillId: String? = null,

    @SerializedName("Date")
    @field:TypeConverters(DateConverter::class)
    var date: Date? = null,

    @SerializedName("Number")
    var number: String? = null,

    @SerializedName("OrgId")
    var orgId: String? = null,

    @SerializedName("OrgName")
    var orgName: String? = null,

    @SerializedName("OrgDeptId")
    var orgDeptId: String? = null,

    @SerializedName("OrgDeptName")
    var orgDeptName: String? = null,

    @SerializedName("VehicleId")
    var vehicleId: String? = null,

    @SerializedName("VehicleName")
    var vehicleName: String? = null,

    @SerializedName("VehicleRegNumber")
    var vehicleRegNumber: String? = null,

    @SerializedName("DriverId")
    var driverId: String? = null,

    @SerializedName("DriverName")
    var driverName: String? = null,

    @SerializedName("StartDatePlan")
    @field:TypeConverters(DateConverter::class)
    var startDatePlan: Date? = null,

    @SerializedName("EndDatePlan")
    @field:TypeConverters(DateConverter::class)
    var endDatePlan: Date? = null,

    @SerializedName("StartDate")
    @field:TypeConverters(DateConverter::class)
    var startDate: Date? = null,

    @SerializedName("EndDate")
    @field:TypeConverters(DateConverter::class)
    var endDate: Date? = null,

    @SerializedName("OdometerStart")
    var odometerStart: Int? = null,

    @SerializedName("OdometerFinish")
    var odometerFinish: Int? = null,

    @SerializedName("Distance")
    var distance: Int? = null,

    @SerializedName("DistanceNavi")
    var distanceNavi: Int? = null,

    @SerializedName("IsEndingDisabled")
    var isEndingDisabled: Boolean? = null,

    @SerializedName("StateId")
    var stateId: String? = null,

    @SerializedName("StateName")
    var stateName: String? = null,

    @SerializedName("IsNaviDeviceAvailable")
    var isNaviDeviceAvailable: Boolean? = null,

    @SerializedName("NaviDeviceId")
    var naviDeviceId: String? = null,

    @SerializedName("IsDTPTest")
    var isDTPTest: Boolean? = null,

    @Ignore
    @SerializedName("CustomerDocs")
    var customerDocs: List<CustomerDoc>? = null,

    @Ignore
    @SerializedName("Equipment")
    var equipments: List<Equipment>? = null,

    @Ignore
    @SerializedName("EquipmentJobs")
    var equipmentJobs: List<EquipmentJob>? = null,

    @Ignore
    @SerializedName("Permissions")
    var permissions: List<Permission>? = null,

    @Ignore
    @SerializedName("FuelCards")
    var fuelCards: List<FuelCard>? = null,

    @Ignore
    @SerializedName("FuelTanks")
    var fuelTanks: List<FuelTank>? = null,

    @Ignore
    @SerializedName("Fuelings")
    var refuelings: List<Refueling>? = null

)