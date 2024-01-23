package br.com.raulreis.instaapp.add.data

import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class AddLocalDataSource: AddDataSource {

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado!")
    }
}