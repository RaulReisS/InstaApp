package br.com.raulreis.instaapp.splash.data

import com.google.firebase.auth.FirebaseAuth

class FireSplashDataSource : SplashDataSource {
    override fun session(callback: SplashCallback) {
        if (FirebaseAuth.getInstance().uid != null)
            callback.onSuccess()
        else
            callback.onFailure()
    }
}