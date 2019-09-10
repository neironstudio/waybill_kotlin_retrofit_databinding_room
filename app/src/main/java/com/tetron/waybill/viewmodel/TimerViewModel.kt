package com.tetron.waybill.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tetron.waybill.data.WorkPauseReason
import com.tetron.waybill.data.repository.TimerRepository
import com.tetron.waybill.model.timer.Timer
import com.tetron.waybill.service.TimerService
import org.koin.core.KoinComponent

class TimerViewModel(app: Application) : AndroidViewModel(app), KoinComponent {

    private val timerRepository: TimerRepository = TimerRepository()
    private val context = getApplication<Application>().applicationContext
    private val intentService = Intent(context, TimerService::class.java)


    fun startService() {
        context.startService(intentService)
    }

    fun stopService() {
        context.stopService(intentService)
    }

    fun getTimer() {
        timerRepository.getTimerData()
    }

    fun updateTimer() {
        timerRepository.updateTimer()
    }

    fun getTimerData(): LiveData<Timer> {
        return timerRepository.getTimer()
    }

    private fun updateTimerType(type: TimerService.TypeTimer) {
        timerRepository.updateTimerType(type)
    }

    private fun updateToLunchTime() {
        updateTimerType(TimerService.TypeTimer.LUNCH)
    }

    fun updateTimerToBaseTime() {
        updateTimerType(TimerService.TypeTimer.BASE)
    }

    fun updateToWorkTime() {
        updateTimerType(TimerService.TypeTimer.WORK)
    }

    private fun updateToCustomerTime() {
        updateTimerType(TimerService.TypeTimer.CUSTOMER)
    }

    private fun updateToDriverTime() {
        updateTimerType(TimerService.TypeTimer.DRIVER)
    }

    fun updateToStopTime() {
        timerRepository.updateToStopTime()
    }

    fun setStoppingReason(reasonString: String) {

        when (reasonString) {
            WorkPauseReason.DRIVER.name -> {
                updateToDriverTime()
            }
            WorkPauseReason.CUSTOMER.name -> {
                updateToCustomerTime()
            }
            WorkPauseReason.LUNCH.name -> {
                updateToLunchTime()
            }
        }

    }

    fun clearTimer() {
        timerRepository.clearTimer()
    }

}