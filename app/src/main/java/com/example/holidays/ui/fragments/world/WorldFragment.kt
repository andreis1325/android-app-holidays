package com.example.holidays.ui.fragments.world

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.R

class WorldFragment : BaseMvpFragment(), WorldView {

    @InjectPresenter
    lateinit var worldPresenter: WorldPresenter

    companion object {

        fun newInstance(): WorldFragment {

            return WorldFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_world

    override fun onViewCreated(view: View) {
        initOnClickListener()
    }

    private fun initOnClickListener() {

    }
}
