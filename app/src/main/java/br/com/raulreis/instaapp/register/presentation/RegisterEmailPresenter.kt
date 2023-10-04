package br.com.raulreis.instaapp.register.presentation


import android.util.Patterns
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.register.RegisterEmail
import br.com.raulreis.instaapp.register.data.RegisterCallback
import br.com.raulreis.instaapp.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private val repository: RegisterRepository
    ) : RegisterEmail.Presenter {

    override fun onDestroy() {
        view = null
    }

    override fun create(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        }
        else {
            view?.displayEmailFailure(null)
        }

        if (isEmailValid) {
            view?.showProgress(true)

            repository.create(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }
}