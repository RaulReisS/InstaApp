package br.com.raulreis.instaapp.profile.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

class ProfileFakeRemoteDataSource: ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() { userUUID == it.uuid}

            if (userAuth != null) {
                callback.onSuccess(userAuth)
            }
            else {
                callback.onFailure("Usuário não encontrado")
            }

            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val posts = Database.posts[userUUID]


            callback.onSuccess(posts?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }
}