package br.com.raulreis.instaapp.common.base

import br.com.raulreis.instaapp.login.data.FakeDataSource
import br.com.raulreis.instaapp.login.data.LoginRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }
}