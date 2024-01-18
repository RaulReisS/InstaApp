package br.com.raulreis.instaapp.login.data

interface LoginCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}