package br.com.raulreis.instaapp.add.data

import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.UserAuth
import java.lang.RuntimeException

class AddLocalDataSource: AddDataSource {

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("Usuário não logado!")
    }
}