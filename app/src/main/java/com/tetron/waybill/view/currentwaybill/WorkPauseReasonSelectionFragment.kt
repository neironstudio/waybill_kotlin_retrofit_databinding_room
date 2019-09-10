package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.WorkPauseReasonSelectionFragmentBinding
import com.tetron.waybill.util.afterTextChanged
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel
import com.tetron.waybill.viewmodel.WorkPauseReasonSelectionViewModel

class WorkPauseReasonSelectionFragment : Fragment() {

    private lateinit var viewDataBinding: WorkPauseReasonSelectionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            WorkPauseReasonSelectionFragmentBinding.inflate(inflater, container, false).apply {

                activity?.let {
                    currentWaybillViewModel =
                        ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
                }

                workPauseReasonSelectionViewModel =
                    ViewModelProviders.of(this@WorkPauseReasonSelectionFragment)[WorkPauseReasonSelectionViewModel::class.java]

                lifecycleOwner = this@WorkPauseReasonSelectionFragment

            }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.etReasonComment.apply {

            afterTextChanged {
                viewDataBinding.workPauseReasonSelectionViewModel?.validateReasonComment(viewDataBinding.etReasonComment.text.toString())
            }

        }

    }

}