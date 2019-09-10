package com.tetron.waybill.view.subsystemselection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tetron.waybill.R
import com.tetron.waybill.view.currentwaybill.CurrentWaybillActivity
import com.tetron.waybill.view.login.LoginActivity
import com.tetron.waybill.viewmodel.SubsystemSelectionViewModel
import kotlinx.android.synthetic.main.activity_subsystem_selection.*

class SubsystemSelectionActivity : AppCompatActivity() {

    private lateinit var subsystemSelectionViewModel: SubsystemSelectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subsystem_selection)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        subsystemSelectionViewModel = ViewModelProviders.of(this)
            .get(SubsystemSelectionViewModel::class.java)

        btnCurrentWaybill.setOnClickListener {
            val intent = Intent(this, CurrentWaybillActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            subsystemSelectionViewModel.logout()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
