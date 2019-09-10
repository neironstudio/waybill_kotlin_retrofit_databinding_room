package com.tetron.waybill.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.data.db.dao.*
import com.tetron.waybill.data.network.api.WebService1C
import com.tetron.waybill.di.AppModuleWebService
import com.tetron.waybill.model.model1c.WaybillDoc
import com.tetron.waybill.model.waybill.CurrentWaybillStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class WebServer1CRepository : KoinComponent {

    private val appModuleWebService: AppModuleWebService by inject()
    private val wayBillDocDao: WaybillDocDao by inject()
    private val customerDocDao: CustomerDocDao by inject()
    private val permissionDao: PermissionDao by inject()
    private val equipmentJobDao: EquipmentJobDao by inject()
    private val equipmentDao: EquipmentDao by inject()
    private val fuelTankDao: FuelTankDao by inject()
    private val fuelCardDao: FuelCardDao by inject()
    private val refuelingsDao: RefuelingDao by inject()
    private val currentWaybillStatusDao: CurrentWaybillStatusDao by inject()

    private val webService1C: WebService1C = appModuleWebService.provideWebService1C()


    fun getOrderResponse() {
        CoroutineScope(Dispatchers.IO).launch {

            val docId = "1c5d7e0d-9f21-11e9-832f-9c8e991e5cb0"

            val request = webService1C.getOrderItemResponse(docId)
            withContext(Dispatchers.IO) {
                try {
                    if (request.isSuccessful) {

                        val waybillDoc = request.body()?.resultData

                        waybillDoc?.let {
                            wayBillDocDao.addWaybillDoc(it)
                        }

                        waybillDoc?.customerDocs?.let {
                            customerDocDao.addCustomerDoc(it)
                        }

                        waybillDoc?.permissions?.let {
                            permissionDao.addPermission(it)
                        }

                        waybillDoc?.equipments?.let {
                            equipmentDao.addEquipments(it)
                        }

                        waybillDoc?.equipmentJobs?.let {
                            equipmentJobDao.addEquipmentJobs(it)
                        }

                        waybillDoc?.fuelTanks?.let {
                            fuelTankDao.addFuelTanks(it)
                        }

                        waybillDoc?.refuelings?.let {
                            refuelingsDao.addRefuelings(it)
                        }

                        waybillDoc?.fuelCards?.let {
                            fuelCardDao.addFuelCards(it)
                        }

                        waybillDoc?.waybillId?.let {
                            currentWaybillStatusDao.addCurrentWaybillStatus(CurrentWaybillStatus(it))
                        }

                    }
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        Log.d(this.toString(), e.message!!)
                    }
                }
            }
        }
    }

    fun getWayBillDocsList(): LiveData<List<WaybillDoc>> {
        return wayBillDocDao.getWaybillDocsList()
    }
}