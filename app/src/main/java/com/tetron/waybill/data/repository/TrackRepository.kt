package com.tetron.waybill.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tetron.waybill.data.db.dao.TrackDao
import com.tetron.waybill.data.network.api.WebServiceOsrm
import com.tetron.waybill.data.network.api.WebServiceWayBillServer
import com.tetron.waybill.di.AppModuleWebServiceOSRM
import com.tetron.waybill.di.AppModuleWebServiceWayBillServer
import com.tetron.waybill.model.track.*
import com.tetron.waybill.util.ConverterUtils
import com.tetron.waybill.util.SubmissionCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TrackRepository : KoinComponent {

    private val trackDao: TrackDao by inject()
    private val appModuleWebServiceOSRM: AppModuleWebServiceOSRM by inject()
    private val appModuleWayBillServer: AppModuleWebServiceWayBillServer by inject()
    private val webServiceOSRM: WebServiceOsrm = appModuleWebServiceOSRM.provideWebServiceOsrm()
    private val webServiceWayBillServer: WebServiceWayBillServer = appModuleWayBillServer.provideWayBillServer()
    private val customerDocRepository = CustomerDocRepository()
    val lengthOsrmCallBack: MutableLiveData<RouteOsrm> = MutableLiveData()

    suspend fun addWayPath(wayPath: WayPath): Long {

        return trackDao.addWayPath(wayPath)
    }

    suspend fun updateWayPathList(wayPath: List<WayPath>): Int {
        return trackDao.updateWayPathList(wayPath)
    }

    suspend fun getWayPathList(activeParams: Boolean, selectAll: Int): List<WayPath> {

        return trackDao.getWayPathList(activeParams, selectAll)
    }

    fun getWayPathLiveDataList(wayType: String): LiveData<List<WayPath>> {
        return trackDao.getAllWayPath(wayType)
    }

    suspend fun getWayItemList(wayPathId: Int): List<WayItem> {
        return trackDao.getWayItemList(wayPathId)
    }

    suspend fun addWayItem(wayItem: WayItem) {
        trackDao.addWayItem(wayItem)
    }

    suspend fun getWayItemMaxId(): WayItem {
        return trackDao.getWayItemMaxId()
    }

    //=======osrm
    fun getLengthFromStringPointArray(stringArrayPoint: String, selectIdPath: Int) {
        webServiceOSRM.getLengthFromPointArray(stringArrayPoint, false).enqueue(object : Callback<RouteOsrm> {

            override fun onResponse(call: Call<RouteOsrm>, response: Response<RouteOsrm>) {
                if (response.isSuccessful) {
                    lengthOsrmCallBack.value = response.body()
                    response.body()?.routeOsrm?.get(0)?.distance?.let { updateWayPath(selectIdPath, it) }
                }
            }

            override fun onFailure(call: Call<RouteOsrm>, t: Throwable) {

            }
        })
    }

    fun updateWayPath(selectIdPath: Int, length: Float) {
        val scope = CoroutineScope(Dispatchers.Default)

        val submitLength = SubmissionCheck.checkSubmissionDistanceToCustomer((length / 1000).toInt())

        scope.launch {
            val wayPath = trackDao.getWayPath(selectIdPath)
            wayPath.finishTime = Date()
            wayPath.length = submitLength
            trackDao.updateWayPath(wayPath)
            var submissionTime = 0
            if (wayPath.finishTime != null && wayPath.startTime != null) {
                val convertTime = ConverterUtils.computeDateDiff(wayPath.finishTime!!, wayPath.startTime!!)
                submissionTime = SubmissionCheck.checkTimeToCustomer(convertTime)
            }
            trackDao.updateWayPath(wayPath)
            updateDistanceTime(wayPath.customerDocId!!, submissionTime, submitLength)
        }


    }

    fun updateDistanceTime(docId: String, timeToCustomer: Int, distanceToCustomer: Int) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            customerDocRepository.updateDistanceTime(
                docId,
                timeToCustomer,
                distanceToCustomer

            )

        }
    }

    //========waybill
    fun postWayPathOrder(wayPathOrder: WayPathOrder) {
        webServiceWayBillServer.postOrder(wayPathOrder).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

            }
        })
    }

    fun postServerRoute(route: Route) {
        webServiceWayBillServer.postRoute(route).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

            }
        })
    }

}