package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PrinterDao {

    @Query("SELECT waybillId FROM waybill_doc LIMIT 1")
    suspend fun getWaybillId(): String

    @Query("SELECT userId FROM userInfo LIMIT 1")
    suspend fun getUserId(): String

}
