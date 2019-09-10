package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.tetron.waybill.model.model1c.Permission

@Dao
interface PermissionDao {

    @Insert(onConflict = REPLACE)
    fun addPermission(permission: List<Permission>)

    @Query("SELECT * FROM permission")
    suspend fun getPermission(): List<Permission>

    @Update
    suspend fun updatePermission(permission: List<Permission>)

    @Query("SELECT issuedBy FROM permission WHERE permissionId = :medicalApprove")
    suspend fun getDoctorName(medicalApprove: String = "MD"): String

    @Query("SELECT issuedBy FROM permission WHERE permissionId = :technicalApprove")
    suspend fun getTechnicianName(technicalApprove: String = "EN"): String

}