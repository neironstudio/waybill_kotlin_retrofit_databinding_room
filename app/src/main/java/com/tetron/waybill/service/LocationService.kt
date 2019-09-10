package com.tetron.waybill.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.model.track.WayItem
import com.tetron.waybill.util.NotificationHelper
import com.tetron.waybill.viewmodel.TrackDataViewModel


class LocationService : LifecycleService() {

    private val mBinder = LocalBinder()
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var trackDataViewModel: TrackDataViewModel? = null
    private var selectWayPathId: Int? = null

    override fun onCreate() {
        super.onCreate()

        trackDataViewModel = TrackDataViewModel()
        Log.d(TAG, "onCreate")
        startForeground(
            1,
            NotificationHelper.makeStatusNotification("Start service", "start track", applicationContext)
        )

        trackDataViewModel?.selectIdWayPath?.observe(this, Observer { integer -> selectWayPathId = integer })
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationCallback()
        createLocationRequest()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.i(TAG, "onStartCommand")
        if (checkSelfPermission()
        ) {
            stopSelf()
            return Service.START_NOT_STICKY
        }

        getLastKnownLocation()
        startPeriodicLocationUpdate()

        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPeriodicLocationUpdate()
        Log.i(TAG, "onDestroy  ")
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return mBinder
    }

    private fun getLastKnownLocation() {
        if (checkSelfPermission()
        ) {
            return
        }
        mFusedLocationClient?.let {
            it.lastLocation
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val lastLocation = task.result
                        storeLocation(lastLocation!!)
                    }
                }
        }
    }

    private fun createLocationRequest() {

        mLocationRequest = LocationRequest()
            .setInterval(1000 * 15)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                val location = locationResult?.lastLocation
                if (location != null) {
                    storeLocation(location)
                }
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "locationResult start")
                }

                locationResult?.locations?.forEach { location1 ->
                    if (BuildConfig.DEBUG) {
                        Log.i(
                            TAG,
                            "locationResult location " + location1.latitude + " " + location1.longitude + " " + location1.accuracy + " " + location1.provider
                        )
                    }
                }

                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "locationResult stop")
                }
            }
        }
    }

    private fun startPeriodicLocationUpdate() {
        if (checkSelfPermission()
        ) {
            return
        }

        mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private fun checkSelfPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun stopPeriodicLocationUpdate() {
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
            ?.addOnCompleteListener { Log.i(TAG, "removed location updates!") }
    }

    fun storeLocation(location: Location) {
        if (BuildConfig.DEBUG) {
            Log.i(
                TAG,
                "periodic location " + location.latitude + " " + location.longitude + " " + location.accuracy + " " + location.provider
            )
        }
        if (location.accuracy <= ACCEPTED_ACCURACY) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "storeLocation: " + "saved!")
            }
            val selectedWayPathId = selectWayPathId// trackDataViewModel.getSelectIdWayPath();
            val wayItem = WayItem()
            wayItem.wayPathId = selectedWayPathId
            wayItem.accuracy = location.accuracy.toDouble()
            wayItem.lan = location.latitude
            wayItem.lon = location.longitude

            trackDataViewModel?.addWayItem(wayItem)

        }
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        Log.i(TAG, "onTaskRemoved: ")
        stopSelf()

    }


    inner class LocalBinder : Binder() {
        val service: LocationService
            get() = this@LocationService
    }

    companion object {


        const val TAG = "LocationService"


        private const val ACCEPTED_ACCURACY = 50f


    }


}
