package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

interface HomeDataSource {

    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun fetchSession() : UserAuth { throw UnsupportedOperationException() }

    fun putFeed(response:List<Post>?) { throw UnsupportedOperationException() }
}