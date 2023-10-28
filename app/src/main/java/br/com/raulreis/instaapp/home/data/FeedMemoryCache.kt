package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.Cache
import br.com.raulreis.instaapp.common.model.Post

object FeedMemoryCache : Cache<List<Post>> {

    private var posts: List<Post>? = null

    override fun isCached(): Boolean {
        return posts != null
    }

    override fun get(key: String): List<Post>? {
        return posts
    }

    override fun put(data: List<Post>) {
        posts = data
    }
}