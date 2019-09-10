package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tetron.waybill.model.model1c.Refueling

@Dao
interface RefuelingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRefuelings(refuelings: List<Refueling>)

}