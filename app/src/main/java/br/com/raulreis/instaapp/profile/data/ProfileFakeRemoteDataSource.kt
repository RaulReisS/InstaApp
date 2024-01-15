package br.com.raulreis.instaapp.profile.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

class ProfileFakeRemoteDataSource: ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() { userUUID == it.uuid}

            if (userAuth != null) {
                if (userAuth == Database.sessionAuth) {
                    callback.onSuccess(Pair(userAuth, null))
                }
                else {
                    val followings = Database.followers[Database.sessionAuth!!.uuid]
                    val destUser = followings?.firstOrNull { it == userUUID }

                    callback.onSuccess(Pair(userAuth, destUser != null))
                }
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

    override fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            var followers = Database.followers[Database.sessionAuth!!.uuid]
            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = followers
            }

            if (isFollow) {
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userUUID)
            } else {
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userUUID)
            }

            callback.onSuccess(true)
            callback.onComplete()
        }, 500)
    }
}