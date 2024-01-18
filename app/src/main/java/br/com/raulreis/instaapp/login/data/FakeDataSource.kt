package br.com.raulreis.instaapp.login.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.model.Database

class FakeDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            // SELECT * FROM USER_AUTH WHERE EMAIL = ? LIMIT 1
            val userAuth = Database.usersAuth.firstOrNull {
                email == it.email
            }

            if (userAuth == null) {
                callback.onFailure("Usuário não encontrado")
            } else if (userAuth.password != password) {
                callback.onFailure("Senha está incorreta")
            } else {
                Database.sessionAuth = userAuth
                callback.onSuccess()
            }

            callback.onComplete()
        }, 2000)
    }
}