package com.example.holidays.ui.fragments.settings

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.R

class SettingsFragment : BaseMvpFragment(), SettingsView {

    @InjectPresenter
    lateinit var settingsPresenter: SettingsPresenter

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun onViewCreated(view: View) {

        setOnClickListeners()
    }

    private fun setOnClickListeners() {

    }
}