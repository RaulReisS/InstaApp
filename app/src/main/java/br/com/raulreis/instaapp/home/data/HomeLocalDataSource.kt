package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.Cache
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth
import java.lang.RuntimeException

class HomeLocalDataSource(
    private val feedCache: Cache<List<Post>>
) : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = feedCache.get(userUUID)
        if (posts != null) {
            callback.onSuccess(posts)
        }
        else {
            callback.onFailure("Posts não existem")
        }
        callback.onComplete()
    }

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("Usuário não logado")
    }

    override fun putFeed(response: List<Post>) {
        feedCache.put(response)
    }
}