package com.tetron.waybill.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.tetron.waybill.util.NotificationHelper
import com.tetron.waybill.viewmodel.TimerViewModel
import java.util.*
import com.tetron.waybill.R



class TimerService : LifecycleService() {
    private val timer = Timer()
    private lateinit var timerViewModel: TimerViewModel
    

    override fun onCreate() {
        super.onCreate()
        timerViewModel = TimerViewModel(applicationContext as Application)
        timer.scheduleAtFixedRate(MainTask(), 0, 1000 * 60)

        Log.d(LocationService.TAG, "onCreate")
        startForeground(
            1,
            NotificationHelper.makeStatusNotification(
                getString(R.string.message_start_timer_title), getString(
                    R.string.message_timer_start_body
                ), applicationContext
            )
        )

        timerViewModel.getTimer()


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LOG_TAG", "Stop")
        timer.cancel()
    }

    private inner class MainTask : TimerTask() {
        override fun run() {

            timerEvent()

        }
    }

    fun timerEvent() {
        timerViewModel.updateTimer()
    }


    enum class TypeTimer {
        BASE,
        WORK,
        LUNCH,
        CUSTOMER,
        DRIVER,
        STOP
    }
}