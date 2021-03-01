package com.example.holidays.ui.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.gallery_settings.ui.base.BaseMvpActivity
import com.example.holidays.ui.fragments.mygallery.MyGalleryFragment
import com.example.holidays.ui.fragments.settings.SettingsFragment
import com.example.holidays.ui.fragments.uploadphotos.UploadPhotosFragment
import com.example.holidays.R
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseMvpActivity(), NavigationView {

    private val fragNavController = FragNavController(supportFragmentManager, R.id.vFlContainer)
    private var backPressed: Long = 0

    companion object {
        private const val TIME: Int = 2000
    }

    private val fragments: List<BaseMvpFragment> = listOf(
        MyGalleryFragment.newInstance(),
        UploadPhotosFragment.newInstance(),
        SettingsFragment.newInstance()
    )

    override fun onBackPressed() {

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
}