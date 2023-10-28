package br.com.raulreis.instaapp.common.base

import br.com.raulreis.instaapp.home.data.FeedMemoryCache
import br.com.raulreis.instaapp.home.data.HomeDataSourceFactory
import br.com.raulreis.instaapp.home.data.HomeRepository
import br.com.raulreis.instaapp.login.data.FakeDataSource
import br.com.raulreis.instaapp.login.data.LoginRepository
import br.com.raulreis.instaapp.profile.data.PostListMemoryCache
import br.com.raulreis.instaapp.profile.data.ProfileDataSourceFactory
import br.com.raulreis.instaapp.profile.data.ProfileMemoryCache
import br.com.raulreis.instaapp.profile.data.ProfileRepository
import br.com.raulreis.instaapp.register.data.FakeRegisterDataSource
import br.com.raulreis.instaapp.register.data.RegisterRepository
import br.com.raulreis.instaapp.splash.data.FakeLocalDataSource
import br.com.raulreis.instaapp.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository(FakeDataSource())
    }

    fun registerEmailRepository() : RegisterRepository {
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun splashRepository() : SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }

    fun profileRepository() : ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository() : HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }
}