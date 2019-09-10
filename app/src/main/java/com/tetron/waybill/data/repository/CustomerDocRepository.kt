package com.tetron.waybill.data.repository


import com.tetron.waybill.data.Result
import com.tetron.waybill.data.Result.Error
import com.tetron.waybill.data.Result.Success
import com.tetron.waybill.data.db.dao.CustomerDocDao
import com.tetron.waybill.model.model1c.CustomerDoc
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class CustomerDocRepository : KoinComponent {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val customerDocDao: CustomerDocDao by inject()

    suspend fun getCurrentCustomerDoc(): Result<CustomerDoc> = withContext(ioDispatcher) {
        return@withContext try {
            Success(customerDocDao.getCurrentCustomerDoc())
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun updateCustomerDoc(customerDoc: CustomerDoc) = withContext(ioDispatcher) {
        customerDocDao.updateCustomerDoc(customerDoc)
    }

    suspend fun updateTimeSpent(
        id: String,
        timeToCustomer: Int,
        distanceToCustomer: Int,
        timeToBase: Int,
        distanceToBase: Int
    ) = withContext(ioDispatcher) {
        customerDocDao.updateTimeSpent(id, timeToCustomer, distanceToCustomer, timeToBase, distanceToBase)
    }

    suspend fun updateDistanceTime(id: String, timeToCustomer: Int, distanceToCustomer: Int) =
        withContext(ioDispatcher) {
            customerDocDao.updateDistanceTime(id, timeToCustomer, distanceToCustomer)
        }

    suspend fun updateContactPerson(id: String, contactPerson: String) = withContext(ioDispatcher) {
        customerDocDao.updateContactPerson(id, contactPerson)
    }

    suspend fun updateContactPersonPosition(id: String, contactPersonPosition: String) =
        withContext(ioDispatcher) {
            customerDocDao.updateContactPersonPosition(id, contactPersonPosition)
        }

    suspend fun updateOdometers(id: String, odometerArrival: Int, odometerDeparture: Int) =
        withContext(ioDispatcher) {
            customerDocDao.updateOdometers(id, odometerArrival, odometerDeparture)
        }

    suspend fun updateMachineKilometers(id: String, machineKilometers: Int) =
        withContext(ioDispatcher) {
            customerDocDao.updateMachineKilometers(id, machineKilometers)
        }

    suspend fun updateTimeTravel(id: String, timeTravel: Int) = withContext(ioDispatcher) {
        customerDocDao.updateTimeTravel(id, timeTravel)
    }

    suspend fun updateAdditionalEquipmentWorkingTime(id: String, additionalEquipmentWorkingTime: Int) =
        withContext(ioDispatcher) {
            customerDocDao.updateAdditionalEquipmentWorkingTime(id, additionalEquipmentWorkingTime)
        }

}