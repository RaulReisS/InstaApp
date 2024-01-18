package br.com.raulreis.instaapp.login.presentation

import android.util.Patterns
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.login.Login
import br.com.raulreis.instaapp.login.data.LoginCallback
import br.com.raulreis.instaapp.login.data.LoginRepository

class LoginPresenter(
    private var view: Login.View?,
    private val repository: LoginRepository
    )  : Login.Presenter {
    override fun login(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        }
        else {
            view?.displayEmailFailure(null)
        }

        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        }
        else {
            view?.displayPasswordFailure(null)
        }

        if (isEmailValid && isPasswordValid) {
            view?.showProgress(true)

            repository.login(email, password, object : LoginCallback {
                override fun onSuccess() {
                    view?.onUserAuthenticated()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthorized(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}