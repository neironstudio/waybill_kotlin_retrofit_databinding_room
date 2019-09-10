package com.tetron.waybill.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.data.db.dao.*
import com.tetron.waybill.model.customerdoc.CurrentCustomerDocStatus
import com.tetron.waybill.model.model1c.*
import com.tetron.waybill.model.timer.Timer
import com.tetron.waybill.model.track.WayItem
import com.tetron.waybill.model.track.WayPath
import com.tetron.waybill.model.waybill.CurrentWaybillStatus

@Database(
    entities = [
        WaybillDoc::class,
        CustomerDoc::class,
        Permission::class,
        Equipment::class,
        EquipmentJob::class,
        FuelTank::class,
        Refueling::class,
        FuelCard::class,
        CurrentWaybillStatus::class,
        CurrentCustomerDocStatus::class,
        WayItem::class,
        WayPath::class,
        UserInfo::class,
        Timer::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun waybillDocDao(): WaybillDocDao
    abstract fun customerDocDao(): CustomerDocDao
    abstract fun permissionDao(): PermissionDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun equipmentJobDao(): EquipmentJobDao
    abstract fun fuelTankDao(): FuelTankDao
    abstract fun refuelingDao(): RefuelingDao
    abstract fun fuelCardDao(): FuelCardDao
    abstract fun currentWaybillStatusDao(): CurrentWaybillStatusDao
    abstract fun currentCustomerDocStatusDao(): CurrentCustomerDocStatusDao
    abstract fun trackDao(): TrackDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun timerDao(): TimerDao
    abstract fun printerDao(): PrinterDao


    companion object {

        @JvmSynthetic
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(ctx: Context) = Room
            .databaseBuilder(ctx, AppDatabase::class.java, "Waybill.db")
            .apply { if (BuildConfig.DEBUG) fallbackToDestructiveMigration() }
            .build()
    }

}