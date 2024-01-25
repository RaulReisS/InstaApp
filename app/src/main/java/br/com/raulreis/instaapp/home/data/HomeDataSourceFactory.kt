package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.Cache
import br.com.raulreis.instaapp.common.model.Post

class HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource(): HomeDataSource {
        return FireHomeDataSource()
    }

    fun createFromFeed(): HomeDataSource {
        if(feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }
        return FireHomeDataSource()
    }

}