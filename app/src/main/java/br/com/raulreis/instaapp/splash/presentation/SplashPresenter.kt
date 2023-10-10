package br.com.raulreis.instaapp.splash.presentation

import br.com.raulreis.instaapp.splash.Splash
import br.com.raulreis.instaapp.splash.data.SplashCallback
import br.com.raulreis.instaapp.splash.data.SplashRepository

class SplashPresenter(
    private var view: Splash.View?,
    private var repository: SplashRepository
    ) : Splash.Presenter {
    override fun onDestroy() {
        view = null
    }

    override fun authenticated() {
        repository.session(object : SplashCallback {
            override fun onSuccess() {
                view?.goToMainScreen()
            }

            override fun onFailure() {
                view?.goToLoginScreen()
            }
        })
    }
}