package br.com.raulreis.instaapp.register.data

interface RegisterCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}