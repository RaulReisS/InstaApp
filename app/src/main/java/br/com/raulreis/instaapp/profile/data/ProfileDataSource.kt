package br.com.raulreis.instaapp.profile.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

interface ProfileDataSource {

    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException()}

    fun fetchSession() : UserAuth { throw UnsupportedOperationException() }

    fun putUser(response: Pair<UserAuth, Boolean?>) { throw UnsupportedOperationException() }

    fun putPosts(response: List<Post>?) { throw UnsupportedOperationException() }
}