package com.tetron.waybill.di

import com.tetron.waybill.data.db.AppDatabase
import com.tetron.waybill.util.SharedPreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module(override = true) {
    single { SharedPreferenceHelper(androidContext()) }
    single { AppModuleWebService(androidContext()) }
    single { AppModuleWebServiceOSRM(androidContext()) }
    single { AppModuleWebServiceWayBillServer(androidContext()) }
    single { AppDatabase.getInstance(androidContext()).waybillDocDao() }
    single { AppDatabase.getInstance(androidContext()).customerDocDao() }
    single { AppDatabase.getInstance(androidContext()).permissionDao() }
    single { AppDatabase.getInstance(androidContext()).equipmentJobDao() }
    single { AppDatabase.getInstance(androidContext()).equipmentDao() }
    single { AppDatabase.getInstance(androidContext()).fuelTankDao() }
    single { AppDatabase.getInstance(androidContext()).refuelingDao() }
    single { AppDatabase.getInstance(androidContext()).fuelCardDao() }
    single { AppDatabase.getInstance(androidContext()).currentWaybillStatusDao() }
    single { AppDatabase.getInstance(androidContext()).currentCustomerDocStatusDao() }
    single { AppDatabase.getInstance(androidContext()).trackDao() }
    single { AppDatabase.getInstance(androidContext()).userInfoDao() }
    single { AppDatabase.getInstance(androidContext()).timerDao() }
    single { AppDatabase.getInstance(androidContext()).printerDao() }
}

