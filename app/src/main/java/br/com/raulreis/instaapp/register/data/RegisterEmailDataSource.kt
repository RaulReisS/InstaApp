package br.com.raulreis.instaapp.register.data

interface RegisterEmailDataSource {
    fun create(email: String,  callback: RegisterEmailCallback)
}