package br.com.raulreis.instaapp.profile.data

import br.com.raulreis.instaapp.common.model.UserAuth

object ProfileMemoryCache : ProfileCache<UserAuth> {

    private var userAuth: UserAuth? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String): UserAuth? {
        if (userAuth?.uuid == key)
            return userAuth
        return null
    }

    override fun put(data: UserAuth) {
        userAuth = data
    }
}