package com.tetron.waybill.viewmodel

import android.app.Application
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tetron.waybill.R
import com.tetron.waybill.data.network.api.WebService1C
import com.tetron.waybill.data.repository.PrinterRepository
import com.tetron.waybill.di.AppModuleWebService
import com.tetron.waybill.model.model1c.PrintRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class PrintViewModel(app: Application) : AndroidViewModel(app), KoinComponent {

    private val printerRepository:PrinterRepository = PrinterRepository()
    val statusPrint: MutableLiveData<Any> = MutableLiveData()
    private val appModuleWebService: AppModuleWebService by inject()
    private val webService1C: WebService1C = appModuleWebService.provideWebService1C()
    var testPrintEnable: Boolean = false

    fun setEnableTestPrint(visibility: Boolean): Boolean {

        testPrintEnable = visibility
        return false
    }

    fun printDoc(): Boolean {
        if (testPrintEnable) {

            CoroutineScope(Dispatchers.IO).launch {
                val waybillDocId = printerRepository.getWaybillDocId()
                val userId = printerRepository.getUserId()

                val request = webService1C.printDoc(PrintRequest(waybillDocId, userId))
                if (request.isSuccessful) {

                    request.body()?.isSuccessful().let {
                        if (it == true) {
                            statusPrint.postValue(true)
                        } else {
                            statusPrint.postValue(request.body()?.errorMessages?.get(0)?.errorMessage)
                        }
                    }


                } else {
                    statusPrint.postValue(
                        getApplication<Application>().applicationContext.getString(
                            R.string.server_error
                        )
                    )
                }
            }
            return true
        } else return false

    }

}