package com.example.holidays.ui.navigation

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.gallery_settings.ui.base.BaseMvpActivity
import com.example.holidays.ui.fragments.world.WorldFragment
import com.example.holidays.ui.fragments.calendar.CalendarFragment
import com.example.holidays.ui.fragments.home.HomeFragment
import com.example.holidays.R
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_navigation.*
import java.lang.System.exit
import kotlin.system.exitProcess

class NavigationActivity : BaseMvpActivity(), NavigationView {

    private val fragNavController = FragNavController(supportFragmentManager, R.id.vFlContainer)
    private var backPressed: Long = 0

    companion object {
        private const val TIME: Int = 2000
    }

    private val fragments: List<BaseMvpFragment> = listOf(
        HomeFragment.newInstance(),
        CalendarFragment.newInstance(),
        WorldFragment.newInstance()
    )

    override fun onBackPressed() {

        exitOrShowMessage()
    }

    @InjectPresenter
    lateinit var accountPresenter: NavigationPresenter

    override fun getLayoutId(): Int = R.layout.activity_navigation

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupNavigation(savedInstanceState)
        setupNavigationListeners()
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        fragNavController.fragmentHideStrategy = FragNavController.HIDE
        fragNavController.rootFragments = fragments
        fragNavController.defaultTransactionOptions = FragNavTransactionOptions
            .newBuilder()
            .transition(FragmentTransaction.TRANSIT_NONE)
            .build()

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    private fun setupNavigationListeners() {
        vBnvBottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myGalleriesPage -> {
                    fragNavController.switchTab(FragNavController.TAB1)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.uploadPhotosPage -> {
                    fragNavController.switchTab(FragNavController.TAB2)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settingsPage -> {
                    fragNavController.switchTab(FragNavController.TAB3)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun exitOrShowMessage() {
        if (backPressed + TIME > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}