package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post

class HomeRepository(private val dataSourceFactory: HomeDataSourceFactory) {

    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putFeed(null)
    }

    fun fetchFeed(callback: RequestCallback<List<Post>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromFeed()

       dataSource.fetchFeed(userId, object : RequestCallback<List<Post>> {
           override fun onSuccess(data: List<Post>) {
               localDataSource.putFeed(data)
               callback.onSuccess(data)
           }

           override fun onFailure(message: String) {
               callback.onFailure(message)
           }

           override fun onComplete() {
               callback.onComplete()
           }
       })
    }

    fun logout() {
        dataSourceFactory.createRemoteDataSource().logout()
    }
}