package com.tetron.waybill.data.repository

import com.tetron.waybill.data.db.dao.PrinterDao
import org.koin.core.KoinComponent
import org.koin.core.inject

class PrinterRepository : KoinComponent {

    private val printerDao: PrinterDao by inject()

    suspend fun getWaybillDocId(): String {
        return printerDao.getWaybillId()
    }

    suspend fun getUserId(): String {
        return printerDao.getUserId()
    }
}