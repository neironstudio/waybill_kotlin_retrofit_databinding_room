package com.tetron.waybill.view.currentwaybill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.databinding.CurrentCustomerDocInfoFragmentBinding
import com.tetron.waybill.viewmodel.CurrentCustomerDocViewModel
import com.tetron.waybill.viewmodel.CurrentWaybillViewModel

class CurrentCustomerDocInfoFragment : Fragment() {

    private lateinit var viewDataBinding: CurrentCustomerDocInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = CurrentCustomerDocInfoFragmentBinding.inflate(inflater, container, false).apply {

            activity?.let {
                currentWaybillViewModel = ViewModelProviders.of(it)[CurrentWaybillViewModel::class.java]
                currentCustomerDocViewModel =
                    ViewModelProviders.of(it)[CurrentCustomerDocViewModel::class.java]
            }

        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewDataBinding.clContactPersonPhoneContainer.setOnClickListener {
            val phoneNumber = viewDataBinding.tvContactPersonPhone.text
            phoneNumber?.let {
                if (it.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(intent)
                }
            }
        }
    }
}