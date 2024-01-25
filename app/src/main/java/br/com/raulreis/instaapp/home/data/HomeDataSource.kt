package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post

interface HomeDataSource {

    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun logout() { throw UnsupportedOperationException() }

    fun fetchSession() : String { throw UnsupportedOperationException() }

    fun putFeed(response:List<Post>?) { throw UnsupportedOperationException() }
}