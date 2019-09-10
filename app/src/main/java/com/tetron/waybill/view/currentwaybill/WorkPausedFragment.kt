package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.data.WorkPauseReason
import com.tetron.waybill.databinding.WorkPausedFragmentBinding
import com.tetron.waybill.model.timer.Timer
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.TimerViewModel
import com.tetron.waybill.viewmodel.WorkPausedViewModel


class WorkPausedFragment : Fragment() {

    private lateinit var viewDataBinding: WorkPausedFragmentBinding
    private var fillingReasonMediatorLiveData: MediatorLiveData<Any> = MediatorLiveData()
    var fillingListObject: HashMap<String, Any> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = WorkPausedFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {
                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]

                timerViewModel =
                    ViewModelProviders.of(it)[TimerViewModel::class.java]
            }

            workPausedViewModel =
                ViewModelProviders.of(this@WorkPausedFragment)[WorkPausedViewModel::class.java]

            lifecycleOwner = this@WorkPausedFragment
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.workPausedViewModel?.pauseReason?.observe(
            this@WorkPausedFragment,
            Observer {
                viewDataBinding.workPausedViewModel?.setReasonText(it)
                viewDataBinding.timerViewModel?.setStoppingReason(it)
            })


        viewDataBinding.workPausedViewModel?.let {
            fillingReasonMediatorLiveData.addSource(
                it.pauseReason
            ) { value ->
                fillingReasonMediatorLiveData.value = value
            }
        }
        viewDataBinding.timerViewModel?.let {
            fillingReasonMediatorLiveData.addSource(
                it.getTimerData()
            ) { value ->
                fillingReasonMediatorLiveData.value = value
            }
        }
        fillingReasonMediatorLiveData.observe(this, Observer {
            if (it is String) {
                fillingListObject["reason"] = it
            }
            if (it is Timer) {
                fillingListObject["timer"] = it
            }
            if (fillingListObject.size == 2) setFragmentElements()

        })

    }

    private fun setFragmentElements() {


        var countTime: Long = 0
        val stopReason = fillingListObject["reason"] as String
        val waybillTimer: Timer = fillingListObject["timer"] as Timer

        when (stopReason) {
            WorkPauseReason.DRIVER.name -> {
                countTime = waybillTimer.countDriverTime ?: 0
            }
            WorkPauseReason.CUSTOMER.name -> {
                countTime = waybillTimer.countCustomerTime ?: 0
            }
            WorkPauseReason.LUNCH.name -> {
                countTime = waybillTimer.countLunchTime ?: 0
            }
        }
        viewDataBinding.count = countTime

    }
}
