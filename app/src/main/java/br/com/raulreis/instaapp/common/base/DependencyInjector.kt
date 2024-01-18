package br.com.raulreis.instaapp.common.base

import android.content.Context
import br.com.raulreis.instaapp.add.data.AddFakeRemoteDataSource
import br.com.raulreis.instaapp.add.data.AddLocalDataSource
import br.com.raulreis.instaapp.add.data.AddRepository
import br.com.raulreis.instaapp.home.data.FeedMemoryCache
import br.com.raulreis.instaapp.home.data.HomeDataSourceFactory
import br.com.raulreis.instaapp.home.data.HomeRepository
import br.com.raulreis.instaapp.login.data.FireLoginDataSource
import br.com.raulreis.instaapp.login.data.LoginRepository
import br.com.raulreis.instaapp.post.data.PostLocalDataSource
import br.com.raulreis.instaapp.post.data.PostRepository
import br.com.raulreis.instaapp.profile.data.PostListMemoryCache
import br.com.raulreis.instaapp.profile.data.ProfileDataSourceFactory
import br.com.raulreis.instaapp.profile.data.ProfileMemoryCache
import br.com.raulreis.instaapp.profile.data.ProfileRepository
import br.com.raulreis.instaapp.register.data.FireRegisterDataSource
import br.com.raulreis.instaapp.register.data.RegisterRepository
import br.com.raulreis.instaapp.search.data.SearchFakeRemoteDataSource
import br.com.raulreis.instaapp.search.data.SearchRepository
import br.com.raulreis.instaapp.splash.data.FakeLocalDataSource
import br.com.raulreis.instaapp.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository(FireLoginDataSource())
    }

    fun registerEmailRepository() : RegisterRepository {
        return RegisterRepository(FireRegisterDataSource())
    }

    fun splashRepository() : SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }

    fun searchRepository() : SearchRepository {
        return SearchRepository(SearchFakeRemoteDataSource())
    }

    fun profileRepository() : ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository() : HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun addRepository() : AddRepository {
        return AddRepository(AddFakeRemoteDataSource(), AddLocalDataSource())
    }

    fun postRepository(context : Context) : PostRepository {
        return PostRepository(PostLocalDataSource(context))
    }
}