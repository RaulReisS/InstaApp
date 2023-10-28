package br.com.raulreis.instaapp.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.Photo
import br.com.raulreis.instaapp.common.model.UserAuth
import java.util.UUID

class FakeRegisterDataSource: RegisterDataSource {
    override fun create(email: String, callback: RegisterCallback) {
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

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() { email == it.email }

            if (userAuth != null) {
                callback.onFailure("Usuário já existe")
            }
            else {

                val newUser = UserAuth(UUID.randomUUID().toString(), name, email, password)

                val created = Database.usersAuth.add(newUser)

                if (created) {
                    Database.sessionAuth = newUser

                    Database.followers[newUser.uuid] = hashSetOf()
                    Database.posts[newUser.uuid] = hashSetOf()
                    Database.feeds[newUser.uuid] = hashSetOf()

                    callback.onSuccess()
                }
                else {
                    callback.onFailure("Erro interno no servidor")
                }

            }

            callback.onComplete()
        }, 2000)
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.sessionAuth

            if (userAuth == null) {
                callback.onFailure("Usuário não encontrado")
            }
            else {

                val newPhoto = Photo(userAuth.uuid, photoUri)

                val created = Database.photos.add(newPhoto)

                if (created) {
                    callback.onSuccess()
                }
                else {
                    callback.onFailure("Erro interno no servidor")
                }

            }

            callback.onComplete()
        }, 2000)
    }
}