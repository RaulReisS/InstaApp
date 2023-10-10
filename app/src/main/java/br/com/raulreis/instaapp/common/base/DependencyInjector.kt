package br.com.raulreis.instaapp.common.base

import br.com.raulreis.instaapp.login.data.FakeDataSource
import br.com.raulreis.instaapp.login.data.LoginRepository
import br.com.raulreis.instaapp.register.data.FakeRegisterDataSource
import br.com.raulreis.instaapp.register.data.RegisterRepository
import br.com.raulreis.instaapp.splash.data.FakeLocalDataSource
import br.com.raulreis.instaapp.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }

    fun registerEmailRepository() :RegisterRepository {
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun splashRepository() : SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }
}