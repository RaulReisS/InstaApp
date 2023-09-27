package br.com.raulreis.instaapp.register.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.model.Database

class FakeRegisterEmailDataSource: RegisterEmailDataSource {
    override fun create(email: String, callback: RegisterEmailCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() { email == it.email }

            if (userAuth == null) {
                callback.onSuccess()
            }
            else {
                callback.onFailure("Usuário já cadastrado")
            }

            callback.onComplete()
        }, 2000)
    }

}