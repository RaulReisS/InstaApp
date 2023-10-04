package br.com.raulreis.instaapp.register

import androidx.annotation.StringRes
import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface RegisterNameAndPassword {

    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirm: String)
    }

    interface  View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun displayNameFailure(@StringRes nameError: Int?)

        fun displayPasswordFailure(@StringRes passwordError: Int?)

        fun onCreateFailure(message: String)

        fun onCreateSuccess(name: String)
    }
}