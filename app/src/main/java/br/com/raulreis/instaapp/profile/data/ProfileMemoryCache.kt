package br.com.raulreis.instaapp.profile.data

import br.com.raulreis.instaapp.common.base.Cache
import br.com.raulreis.instaapp.common.model.User

object ProfileMemoryCache : Cache<Pair<User, Boolean?>> {

    private var userAuth: Pair<User, Boolean?>? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String): Pair<User, Boolean?>? {
        if (userAuth?.first?.uuid == key)
            return userAuth
        return null
    }

    override fun put(data: Pair<User, Boolean?>?) {
        userAuth = data
    }
}