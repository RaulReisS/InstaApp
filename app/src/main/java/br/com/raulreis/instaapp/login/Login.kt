package br.com.raulreis.instaapp.login

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface Login {

    // Camada Presenter
    interface Presenter : BasePresenter {
        fun login(email: String, password : String)
    }

    // Camada View
    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)

    }
}