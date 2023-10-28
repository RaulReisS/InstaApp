package br.com.raulreis.instaapp.home.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.Post

class HomeFakeRemoteDataSource: HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feeds = Database.feeds[userUUID]


            callback.onSuccess(feeds?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }
}