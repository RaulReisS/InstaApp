package br.com.raulreis.instaapp.splash

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView

interface Splash {
    interface Presenter : BasePresenter {
        fun authenticated()
    }

    interface View : BaseView<Presenter> {
        fun goToMainScreen()

        fun goToLoginScreen()
    }
}