package com.tetron.waybill.view.currentwaybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.ApprovalSelectionFragmentBinding
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel

class ApprovalSelectionFragment : Fragment() {

    private lateinit var viewDataBinding: ApprovalSelectionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ApprovalSelectionFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {
                currentWaybillViewModel = ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
            }

        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

}