package br.com.raulreis.instaapp.common.base

import br.com.raulreis.instaapp.login.data.FakeDataSource
import br.com.raulreis.instaapp.login.data.LoginRepository
import br.com.raulreis.instaapp.register.data.FakeRegisterEmailDataSource
import br.com.raulreis.instaapp.register.data.RegisterEmailRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }

    fun registerEmailRepository() :RegisterEmailRepository {
        return RegisterEmailRepository(FakeRegisterEmailDataSource())
    }
}