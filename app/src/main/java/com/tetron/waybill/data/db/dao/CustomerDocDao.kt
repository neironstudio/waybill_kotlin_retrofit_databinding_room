package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.tetron.waybill.model.model1c.CustomerDoc

@Dao
interface CustomerDocDao {

    @Insert(onConflict = REPLACE)
    fun addCustomerDoc(customerDocs: List<CustomerDoc>)

    @Query("SELECT * FROM customer_doc LIMIT 1")
    suspend fun getCurrentCustomerDoc(): CustomerDoc

    @Query("UPDATE customer_doc SET timeToCustomer = :timeToCustomer, distanceToCustomer = :distanceToCustomer, timeToBase = :timeToBase, distanceToBase = :distanceToBase WHERE id = :id")
    suspend fun updateTimeSpent(
        id: String,
        timeToCustomer: Int,
        distanceToCustomer: Int,
        timeToBase: Int,
        distanceToBase: Int
    )

    @Query("UPDATE customer_doc SET timeToCustomer = :timeToCustomer, distanceToCustomer = :distanceToCustomer WHERE id = :id")
    suspend fun updateDistanceTime(id: String, timeToCustomer: Int, distanceToCustomer: Int)

    @Query("UPDATE customer_doc SET contactPerson = :contactPerson WHERE id = :id")
    suspend fun updateContactPerson(id: String, contactPerson: String)

    @Query("UPDATE customer_doc SET contactPersonPosition = :contactPersonPosition WHERE id = :id")
    suspend fun updateContactPersonPosition(id: String, contactPersonPosition: String)

    @Query("UPDATE customer_doc SET odometerStart = :odometerArrival, odometerFinish = :odometerDeparture WHERE id = :id")
    suspend fun updateOdometers(id: String, odometerArrival: Int, odometerDeparture: Int)

    @Query("UPDATE customer_doc SET executedDistance = :machineKilometers WHERE id = :id")
    suspend fun updateMachineKilometers(id: String, machineKilometers: Int)

    @Query("UPDATE customer_doc SET executedTime = :timeTravel WHERE id = :id")
    suspend fun updateTimeTravel(id: String, timeTravel: Int)

    @Query("UPDATE customer_doc SET executedEquipmentTime = :additionalEquipmentWorkingTime WHERE id = :id")
    suspend fun updateAdditionalEquipmentWorkingTime(id: String, additionalEquipmentWorkingTime: Int)

    @Update
    suspend fun updateCustomerDoc(customerDoc: CustomerDoc)

    @Query("UPDATE current_waybill_status SET medicalApprove = :medicalApprove, technicalApprove = :technicalApprove")
    suspend fun resetApprovals(medicalApprove: Boolean = false, technicalApprove: Boolean = false)

}