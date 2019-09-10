package com.tetron.waybill.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tetron.waybill.data.db.dao.TimerDao
import com.tetron.waybill.model.timer.Timer
import com.tetron.waybill.service.TimerService
import com.tetron.waybill.util.SubmissionCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class TimerRepository : KoinComponent {

    var selectTimer: MutableLiveData<Timer> = MutableLiveData()
    private val timerDao: TimerDao by inject()
    private lateinit var _timer: Timer
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getTimerData() {

        coroutineScope.launch {

            val customerDocId = timerDao.getCurrentCustomerDoc().id
            customerDocId?.let {

                val timer: Timer? =
                    timerDao.getTimer(it)
                selectTimer.postValue(timer)
            }
        }
    }

    fun getTimer(): LiveData<Timer> {
        return timerDao.getTimer()
    }

    fun updateToStopTime() {

        coroutineScope.launch {
            val customerDocId = timerDao.getCurrentCustomerDoc().id
            val currentTimer: Timer? = customerDocId?.let { timerDao.getTimer(it) }

            currentTimer?.countBaseTime?.let {
                val count: Long = SubmissionCheck.checkTimeToCustomer(it.toInt()).toLong()
                timerDao.updateTimerFromBase(count)
            }

            updateTimerType(TimerService.TypeTimer.STOP)

        }
    }

    fun updateTimerType(type: TimerService.TypeTimer) {

        coroutineScope.launch {
            val customerDocId = timerDao.getCurrentCustomerDoc().id
            customerDocId?.let { timerDao.updateTimerType(it, type.name) }

        }
    }

    fun updateTimer() {

        coroutineScope.launch {

            val customerDocId = timerDao.getCurrentCustomerDoc().id
            customerDocId?.let {

                val currentTimer = timerDao.getTimer(customerDocId)

                if (currentTimer == null) {

                    val startTime = Date()
                    val timer = Timer()
                    timer.customerDocId = customerDocId
                    timer.startTime = startTime
                    timer.currentState = TimerService.TypeTimer.BASE.name
                    timerDao.addTimer(timer)
                    _timer = timer

                } else {

                    timerDao.getTimer(customerDocId)?.let { _timer = it }

                }
            }
            updateTimerCount(_timer)
        }
    }

    private fun updateTimerCount(timer: Timer) {

        when (timer.currentState) {

            TimerService.TypeTimer.BASE.name -> {
                timer.countBaseTime =
                    incrementTimes(timer.countBaseTime)
            }

            TimerService.TypeTimer.WORK.name -> {
                timer.countWorkTime =
                    incrementTimes(timer.countWorkTime)
            }

            TimerService.TypeTimer.LUNCH.name -> {
                timer.countLunchTime =
                    incrementTimes(timer.countLunchTime)
            }

            TimerService.TypeTimer.CUSTOMER.name -> {
                timer.countCustomerTime = incrementTimes(timer.countCustomerTime)
            }

            TimerService.TypeTimer.DRIVER.name -> {
                timer.countDriverTime = incrementTimes(timer.countDriverTime)
            }
        }

        timerDao.update(timer)

    }

    private fun incrementTimes(_countTime: Long?): Long {
        // fun is increased minutes , if minutes is 59 next value minutes 00 value hour inc to +1
        val count: Long
        val countTime = _countTime ?: 0
        count = if (countTime % 100 == 59L) {
            countTime + 41L
        } else countTime + 1L

        return count

    }

    fun clearTimer() {

        coroutineScope.launch {
            timerDao.clearTimer()
        }

    }
}