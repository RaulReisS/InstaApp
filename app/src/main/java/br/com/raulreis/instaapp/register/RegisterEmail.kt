package br.com.raulreis.instaapp.register

import androidx.annotation.StringRes
import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface RegisterEmail {

    interface Presenter : BasePresenter {
        fun create(email: String)
    }

    interface  View: BaseView<Presenter> {
        fun displayEmailFailure(@StringRes emailError: Int?)
    }
}