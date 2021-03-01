package com.example.holidays.ui.fragments.uploadphotos

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.R

class UploadPhotosFragment : BaseMvpFragment(), UploadPhotosView {

    @InjectPresenter
    lateinit var uploadPhotosPresenter: UploadPhotosPresenter

    companion object {

        fun newInstance(): UploadPhotosFragment {

            return UploadPhotosFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_upload_photos

    override fun onViewCreated(view: View) {
        initOnClickListener()
    }

    private fun initOnClickListener() {

    }
}