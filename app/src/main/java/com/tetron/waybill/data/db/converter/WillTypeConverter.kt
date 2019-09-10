package com.tetron.waybill.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tetron.waybill.model.model1c.CustomerDoc
import com.tetron.waybill.model.model1c.EquipmentJob
import com.tetron.waybill.model.model1c.FuelTank
import com.tetron.waybill.model.model1c.Refueling


object WillTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromCustomerDocsList(customerDocList: List<CustomerDoc>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<CustomerDoc>>() {}.type

        return gson.toJson(customerDocList, type)
    }

    @TypeConverter
    @JvmStatic
    fun toCustomerDocsList(customerDocString: String?): List<CustomerDoc>? {
        val gson = Gson()
        val type = object : TypeToken<List<CustomerDoc>>() {}.type

        return gson.fromJson<List<CustomerDoc>>(customerDocString, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromEquipmentJobsList(equipmentJobList: List<EquipmentJob>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<EquipmentJob>>() {}.type

        return gson.toJson(equipmentJobList, type)
    }

    @TypeConverter
    @JvmStatic
    fun toEquipmentJobsList(equipmentJobListString: String?): List<EquipmentJob>? {
        val gson = Gson()
        val type = object : TypeToken<List<EquipmentJob>>() {}.type

        return gson.fromJson<List<EquipmentJob>>(equipmentJobListString, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromFuelTanksList(fuelTankList: List<FuelTank>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<FuelTank>>() {}.type

        return gson.toJson(fuelTankList, type)
    }

    @TypeConverter
    @JvmStatic
    fun toFuelTanksList(fuelTanksListString: String?): List<FuelTank>? {
        val gson = Gson()
        val type = object : TypeToken<List<FuelTank>>() {}.type

        return gson.fromJson<List<FuelTank>>(fuelTanksListString, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromFuelingsListString(fuelingList: List<Refueling>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Refueling>>() {}.type

        return gson.toJson(fuelingList, type)
    }

    @TypeConverter
    @JvmStatic
    fun toFuelingList(fuelingListString: String?): List<Refueling>? {
        val gson = Gson()
        val type = object : TypeToken<List<Refueling>>() {}.type

        return gson.fromJson<List<Refueling>>(fuelingListString, type)
    }

}