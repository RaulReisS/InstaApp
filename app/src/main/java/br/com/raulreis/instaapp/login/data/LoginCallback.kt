package br.com.raulreis.instaapp.login.data

import br.com.raulreis.instaapp.common.model.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth)
    fun onFailure(message: String)
    fun onComplete()
}