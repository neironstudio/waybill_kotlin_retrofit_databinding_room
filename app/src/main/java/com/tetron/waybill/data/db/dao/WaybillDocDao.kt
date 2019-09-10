package com.tetron.waybill.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tetron.waybill.model.model1c.WaybillDoc

@Dao
interface WaybillDocDao {

    @Insert(onConflict = REPLACE)
    fun addWaybillDoc(waybillDoc: WaybillDoc)

    @Query("SELECT * FROM waybill_doc")
    fun getWaybillDocsList(): LiveData<List<WaybillDoc>>

}