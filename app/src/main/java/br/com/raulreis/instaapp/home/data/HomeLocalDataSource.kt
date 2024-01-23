package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.Cache
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import com.google.firebase.auth.FirebaseAuth
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

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }
}