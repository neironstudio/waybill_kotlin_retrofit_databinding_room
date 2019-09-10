package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.repository.CustomerDocRepository
import com.tetron.waybill.data.repository.TrackRepository
import com.tetron.waybill.model.track.*
import com.tetron.waybill.service.DataControllerWayPathSelected
import com.tetron.waybill.util.GeoUtils
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import java.util.*


class TrackDataViewModel : ViewModel(), KoinComponent {

    private val trackRepository: TrackRepository = TrackRepository()
    private val customerDocRepository: CustomerDocRepository = CustomerDocRepository()
    var selectIdWayPath = DataControllerWayPathSelected.controllerInstance.selectedWayPathId
    var customerDocId: String? = null
    var htmlString = ""

    fun addWayPath(wayType: String) {
        getLengthFromOsrmService()
        viewModelScope.launch {


            val newWayPath = WayPath()
            val getCustomerDocsResult = customerDocRepository.getCurrentCustomerDoc()

            if (getCustomerDocsResult is Result.Success) {
                newWayPath.customerDocId = getCustomerDocsResult.data.id
                customerDocId = getCustomerDocsResult.data.id
            }

            newWayPath.activ = true
            newWayPath.startTime = Date()
            newWayPath.wayType = wayType

            val updateList = trackRepository.getWayPathList(true, 2)

            for (updateItem: WayPath in updateList) {
                updateItem.activ = false
            }
            updateWayPathList(updateList)
            val idWayPath = trackRepository.addWayPath(newWayPath)
            selectIdWayPath.value = idWayPath.toInt()


        }
    }

    fun getLengthFromOsrmService() {
        var searchPoint = ""
        if (selectIdWayPath.value != null) {

            viewModelScope.launch {
                val wayItemList = trackRepository.getWayItemList(selectIdWayPath.value!!)
                for (wayItem: WayItem in wayItemList) {
                    searchPoint += wayItem.lon.toString() + "," + wayItem.lan.toString() + ";"
                }
                if (searchPoint.length > 1) {
                    searchPoint = searchPoint.substring(0, searchPoint.length - 1)
                }
                trackRepository.getLengthFromStringPointArray(searchPoint, selectIdWayPath.value!!)

            }
        }
    }

    fun getWayPathLiveDataList(wayType: String): LiveData<List<WayPath>> {
        return trackRepository.getWayPathLiveDataList(wayType)
    }

    fun postWayPathWayBillServer() {
        viewModelScope.launch {

            htmlString = "http://91.242.34.140:9966/?z=6&center="
            val wayItemList = trackRepository.getWayItemList(selectIdWayPath.value!!)
            for (wayItem: WayItem in wayItemList) {
                htmlString += "&loc=" + wayItem.lan.toString() + "%2C" + wayItem.lon
            }
            htmlString += "&hl=ru&alt=0"

            val wayPathOrder = WayPathOrder()
            wayPathOrder.customerId = selectIdWayPath.value
            wayPathOrder.comment = customerDocId
            wayPathOrder.length = 5555

            trackRepository.postWayPathOrder(wayPathOrder)

            val route = Route()
            route.orderId = selectIdWayPath.value
            route.userId = "test"
            route.osrmHtml = htmlString
            trackRepository.postServerRoute(route)


        }
    }

    fun lengthOsrmCallBack(): LiveData<RouteOsrm> {
        return trackRepository.lengthOsrmCallBack
    }

    private suspend fun updateWayPathList(updateList: List<WayPath>): Int {

        return trackRepository.updateWayPathList(updateList)

    }

    @Suppress("SENSELESS_COMPARISON")
    fun addWayItem(wayItem: WayItem) {
        viewModelScope.launch {
            val lastWayItem = trackRepository.getWayItemMaxId()
            if (lastWayItem != null) {
                val lengthFromPointToPoint =
                    GeoUtils.computeDistanceBetween(
                        lastWayItem.lan ?: 0.toDouble(),
                        lastWayItem.lon ?: 0.toDouble(),
                        wayItem.lan ?: 0.toDouble(),
                        wayItem.lon ?: 0.toDouble()
                    )
                if (lengthFromPointToPoint > 30) {
                    trackRepository.addWayItem(wayItem)
                }
            } else trackRepository.addWayItem(wayItem)

        }
    }

    enum class TypeWay {
        FROM_BASE_TO_CUSTOMER,
        WAY_ON_CUSTOMER
    }
}