package br.com.raulreis.instaapp.register

import android.net.Uri
import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface RegisterPhoto {

    interface Presenter : BasePresenter {
        fun updateUser(photoUri: Uri)
    }

    interface  View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun onUpdateFailure(message: String)

        fun onUpdateSuccess()
    }
}