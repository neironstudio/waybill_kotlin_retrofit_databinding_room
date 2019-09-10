package com.tetron.waybill.data.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tetron.waybill.model.model1c.FuelTank

@Dao
interface FuelTankDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFuelTanks(fuelTanks: List<FuelTank>)

}