package com.example.holidays.ui.fragments.mygallery

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.R

class MyGalleryFragment : BaseMvpFragment(), MyGalleryView {

    @InjectPresenter
    lateinit var myGalleryPresenter: MyGalleryPresenter

    companion object {

        fun newInstance(): MyGalleryFragment {

            return MyGalleryFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_my_gallery

    override fun onViewCreated(view: View) {
        initOnClickListener()
    }

    private fun initOnClickListener() {

    }
}
