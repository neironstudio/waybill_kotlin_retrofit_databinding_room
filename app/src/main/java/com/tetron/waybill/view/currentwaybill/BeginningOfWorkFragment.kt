package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.BeginningOfWorkFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.TimerViewModel
import com.tetron.waybill.viewmodel.TrackDataViewModel

class BeginningOfWorkFragment : Fragment() {

    private lateinit var viewDataBinding: BeginningOfWorkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = BeginningOfWorkFragmentBinding.inflate(inflater, container, false).apply {
            currentWaybillViewModel = ViewModelProviders.of(activity!!).get(CurrentWaybillViewModel::class.java)
            trackDataViewModel = ViewModelProviders.of(activity!!).get(TrackDataViewModel::class.java)
            timerViewModel = ViewModelProviders.of(activity!!).get(TimerViewModel::class.java)
        }
        return viewDataBinding.root
    }


}