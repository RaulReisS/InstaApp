package br.com.raulreis.instaapp.login.data

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)
}