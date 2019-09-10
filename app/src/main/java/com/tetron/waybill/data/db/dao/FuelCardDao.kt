package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tetron.waybill.model.model1c.FuelCard

@Dao
interface FuelCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFuelCards(fuelCards: List<FuelCard>)

}