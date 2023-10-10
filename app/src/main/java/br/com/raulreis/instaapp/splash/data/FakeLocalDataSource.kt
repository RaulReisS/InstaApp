package br.com.raulreis.instaapp.splash.data

import br.com.raulreis.instaapp.common.model.Database

class FakeLocalDataSource : SplashDataSource {
    override fun session(callback: SplashCallback) {
        if (Database.sessionAuth != null)
            callback.onSuccess()
        else
            callback.onFailure()
    }
}