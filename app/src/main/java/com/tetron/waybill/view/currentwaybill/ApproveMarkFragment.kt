package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.ApproveMarkFragmentBinding
import com.tetron.waybill.model.waybill.PinState
import com.tetron.waybill.util.EventObserver
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.viewmodel.ApproveMarkViewModel
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel

class ApproveMarkFragment : Fragment() {

    private lateinit var viewDataBinding: ApproveMarkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ApproveMarkFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {
                currentWaybillViewModel =
                    ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
            }

            approveMarkViewModel =
                ViewModelProviders.of(this@ApproveMarkFragment)[ApproveMarkViewModel::class.java]

            lifecycleOwner = this@ApproveMarkFragment
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.approveMarkViewModel?.apply {
            pinState.observe(this@ApproveMarkFragment, Observer<PinState> { pinState ->
                pinState?.let {
                    if (pinState.pinError != null) {
                        viewDataBinding.tilPIN.error = getString(pinState.pinError)
                    } else {
                        viewDataBinding.tilPIN.error = null
                    }
                }
            })

            viewDataBinding.etPIN.apply {

                afterTextChanged {
                    viewDataBinding.approveMarkViewModel?.pinChanged(viewDataBinding.etPIN.text.toString())
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            if (viewDataBinding.btnCheckPin.isEnabled) {
                                viewDataBinding.btnCheckPin.callOnClick()
                            }
                    }
                    false
                }

            }

        }

        viewDataBinding.currentWaybillViewModel?.showApproveMarkFragmentEvent?.observe(
            this@ApproveMarkFragment,
            Observer {
                viewDataBinding.approveMarkViewModel?.setup(it.peekContent())
            })

        viewDataBinding.approveMarkViewModel?.closedApproveMarkFragmentEvent?.observe(
            this@ApproveMarkFragment, EventObserver {
                viewDataBinding.currentWaybillViewModel?.updateCurrentStatus()
            }
        )

    }

}