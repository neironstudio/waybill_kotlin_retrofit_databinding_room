package com.tetron.waybill.view.currentwaybill

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tetron.waybill.R
import com.tetron.waybill.data.WaybillStep
import com.tetron.waybill.service.LocationService
import com.tetron.waybill.service.TimerService
import com.tetron.waybill.util.EventObserver
import com.tetron.waybill.util.replaceFragment
import com.tetron.waybill.util.replaceFragmentWithBackStack
import com.tetron.waybill.view.draw.DrawSignFragment
import com.tetron.waybill.viewmodel.*
import com.tetron.waybill.viewmodel.TrackDataViewModel.TypeWay.FROM_BASE_TO_CUSTOMER
import kotlinx.android.synthetic.main.activity_current_waybill.*

class CurrentWaybillActivity : AppCompatActivity() {

    private lateinit var currentWaybillViewModel: CurrentWaybillViewModel
    private lateinit var currentCustomerDocViewModel: CurrentCustomerDocViewModel
    private lateinit var currentCustomerDocStatusViewModel: CurrentCustomerDocStatusViewModel
    private lateinit var simpleSwitchFragmentEventViewModel: SimpleSwitchFragmentEventViewModel
    private lateinit var dialogEventViewModel: DialogEventViewModel
    private lateinit var trackDataViewModel: TrackDataViewModel
    private lateinit var timerViewModel: TimerViewModel
    private var activeFullScreenFragment: Boolean = false


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_current_waybill -> {
                    showCurrentStepFragment(currentWaybillViewModel.getCurrentWaybillStep())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_information -> {
                    replaceFragment(R.id.waybillMainView, CurrentCustomerDocInfoFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_photo -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_force_majeure -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_current_waybill)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.app_title_back)

        bottom_navigation_menu.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        initializationViewModels()

        createWaybillStepObservers()
        createApprovalSelectionObservers()
        createWorkOnCustomerDocObservers()
        createTrackServiceObservers()
        createSimpleSwitchEventObservers()
        createTimerServiceObservers()

    }

    private fun initializationViewModels() {

        currentWaybillViewModel = ViewModelProviders.of(this)[CurrentWaybillViewModel::class.java]

        currentCustomerDocViewModel =
            ViewModelProviders.of(this)[CurrentCustomerDocViewModel::class.java]

        currentCustomerDocStatusViewModel =
            ViewModelProviders.of(this)[CurrentCustomerDocStatusViewModel::class.java]

        simpleSwitchFragmentEventViewModel =
            ViewModelProviders.of(this)[SimpleSwitchFragmentEventViewModel::class.java]

        dialogEventViewModel = ViewModelProviders.of(this)[DialogEventViewModel::class.java]

        trackDataViewModel = ViewModelProviders.of(this)[TrackDataViewModel::class.java]

        timerViewModel = ViewModelProviders.of(this)[TimerViewModel::class.java]

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var requestLocationIsApproved = false
        for ((index, permission) in permissions.withIndex()) {
            if (permission == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[index] == 0) {
                requestLocationIsApproved = true
            }
        }
        if (requestLocationIsApproved) {
            currentWaybillViewModel.setPermissionLocation(true)

        } else {
            currentWaybillViewModel.setPermissionLocation(false)

        }

    }

    private fun createWaybillStepObservers() {

        currentWaybillViewModel.currentWaybillStep.observe(
            this@CurrentWaybillActivity,
            Observer<WaybillStep> { WaybillStep ->
                WaybillStep?.let {
                    showCurrentStepFragment(it)
                }
            })

    }

    private fun createApprovalSelectionObservers() {

        currentWaybillViewModel.switchWaybillStepEvent.observe(this, EventObserver {
            currentWaybillViewModel.switchWaybillStep()
        })

        currentWaybillViewModel.currentWaybillStatus.observe(this, Observer {
            currentWaybillViewModel.checkApprovalsStatus()
        })

        currentWaybillViewModel.showApprovalSelectionFragmentEvent.observe(this, EventObserver {
            showApprovalSelectionFragment()
        })

        currentWaybillViewModel.showApproveMarkFragmentEvent.observe(this, EventObserver {
            showApproveMarkFragment()
        })

    }

    private fun createWorkOnCustomerDocObservers() {

        currentWaybillViewModel.currentCustomerDocStatus.observe(this, Observer {
            currentWaybillViewModel.checkWorkStatus()
        })

        currentWaybillViewModel.showWorkOnCustomerDocFragmentEvent.observe(this, EventObserver {
            showWorkOnCustomerDocFragment()
        })

        currentWaybillViewModel.showStopReasonSelectionFragmentEvent.observe(this, EventObserver {
            showStopReasonSelectionFragment()
        })

        currentWaybillViewModel.showWorkStoppedFragmentEvent.observe(this, EventObserver {
            showWorkStoppedFragment()
        })

    }

    private fun createTrackServiceObservers() {
        val locationServiceIntent = Intent(this, LocationService::class.java)
        currentWaybillViewModel.stateRunServiceLocation.observe(this, Observer {
            if (it) {
                trackDataViewModel.addWayPath(FROM_BASE_TO_CUSTOMER.name)
                startService(locationServiceIntent)
            } else stopService(locationServiceIntent)
        })

        trackDataViewModel.lengthOsrmCallBack().observe(this, Observer {
            Toast.makeText(
                this,
                "Track Length " + it.routeOsrm?.get(0)?.distance.toString(),
                Toast.LENGTH_LONG
            ).show()
        })

    }

    private fun createTimerServiceObservers() {
        val timerServiceIntent = Intent(this, TimerService::class.java)

        timerViewModel.getTimerData().observe(this, Observer {
            it?.let { timer ->
                timer.currentState?.let {
                    if (timer.currentState == TimerService.TypeTimer.STOP.name) {
                        stopService(timerServiceIntent)
                    } else {
                        startService(timerServiceIntent)
                    }
                }

            }

        })

    }

    private fun createSimpleSwitchEventObservers() {

        simpleSwitchFragmentEventViewModel.signDrawFragment.observe(this, Observer {
            replaceFragmentWithBackStack(R.id.waybillMainViewFullScr, DrawSignFragment())
        })

        simpleSwitchFragmentEventViewModel.customerMarkFragment.observe(this, Observer {
            popBackStackImmediate(supportFragmentManager)
            replaceFragmentWithBackStack(R.id.waybillMainView, CustomerMarkFragment())
        })

        simpleSwitchFragmentEventViewModel.typesetFullScreenFragment.observe(this, Observer {
            activeFullScreenFragment = false
            hideSystemUI()
        })

        simpleSwitchFragmentEventViewModel.typeUnSetFullScreenFragment.observe(this, Observer {

            activeFullScreenFragment = true
            showSystemUI()
        })

    }

    private fun showCurrentStepFragment(step: WaybillStep) {
        when (step) {
            WaybillStep.PRINT -> {
                replaceFragment(R.id.waybillMainView, PrintWaybillFragment())
            }
            WaybillStep.APPROVAL_SELECTION -> {
                currentWaybillViewModel.checkApprovalsStatus()
            }
            WaybillStep.PRINT_COMPLETED_WAYBILL -> {
                replaceFragment(R.id.waybillMainView, PrintWaybillFragment())
            }
            WaybillStep.DEPARTURE_TO_CUSTOMER -> {
                replaceFragment(R.id.waybillMainView, DepartureToCustomerFragment())
            }
            WaybillStep.BEGINNING_OF_WORK -> {
                replaceFragment(R.id.waybillMainView, BeginningOfWorkFragment())
            }
            WaybillStep.WORK_ON_CUSTOMER_DOC -> {
                currentWaybillViewModel.checkWorkStatus()
            }
            WaybillStep.TIME_SPENT -> {
                replaceFragment(R.id.waybillMainView, TimeSpentFragment())
            }
            WaybillStep.CUSTOMER_MARK -> {
                replaceFragment(R.id.waybillMainView, CustomerMarkFragment())
            }
            WaybillStep.RETURN_TO_BASE -> {
                if (!activeFullScreenFragment) showSystemUI()
                replaceFragment(R.id.waybillMainView, ReturnToBaseFragment())
            }
            WaybillStep.FUEL -> {
                replaceFragment(R.id.waybillMainView, FuelFragment())
            }
            WaybillStep.PERFORMANCE_INDICATORS -> {
                replaceFragment(R.id.waybillMainView, PerformanceIndicatorsFragment())
            }
            WaybillStep.WAYBILL_CLOSURE -> {
                replaceFragment(R.id.waybillMainView, WaybillClosureFragment())
            }
            WaybillStep.UNKNOWN -> {
                replaceFragment(R.id.waybillMainView, UnknownFragment())
            }
        }
    }

    private fun showApprovalSelectionFragment() {
        replaceFragment(R.id.waybillMainView, ApprovalSelectionFragment())
    }

    private fun showApproveMarkFragment() {
        replaceFragment(R.id.waybillMainView, ApproveMarkFragment())
    }

    private fun showStopReasonSelectionFragment() {
        replaceFragment(R.id.waybillMainView, WorkPauseReasonSelectionFragment())
    }

    private fun showWorkOnCustomerDocFragment() {
        replaceFragment(R.id.waybillMainView, WorkOnCustomerDocFragment())
    }

    private fun showWorkStoppedFragment() {
        replaceFragment(R.id.waybillMainView, WorkPausedFragment())
    }

    private fun hideSystemUI() {
        this.supportActionBar?.hide()
        this.nestedScrollView.isVisible = false
        this.bottom_navigation_menu.isVisible = false
        this.waybillMainViewFullScr.visibility = View.VISIBLE
    }

    private fun showSystemUI() {
        this.supportActionBar?.show()
        this.bottom_navigation_menu.isVisible = true
        this.nestedScrollView.isVisible = true
        this.waybillMainViewFullScr.visibility = View.GONE
    }

    private fun popBackStackImmediate(supportFragmentManager: FragmentManager) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (!activeFullScreenFragment) showSystemUI()
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentBottomNavItemId", bottom_navigation_menu.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            super.onRestoreInstanceState(savedInstanceState)
            bottom_navigation_menu.selectedItemId =
                savedInstanceState.getInt("currentBottomNavItemId")
        }
    }

}
