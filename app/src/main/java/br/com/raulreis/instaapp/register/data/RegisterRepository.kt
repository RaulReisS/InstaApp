package br.com.raulreis.instaapp.register.data

class RegisterRepository(
    private val dataSource: RegisterDataSource
    ) {

    fun create(email: String, callback: RegisterCallback) {
        dataSource.create(email, callback)
    }

    fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        dataSource.create(email, name, password, callback)
    }

}