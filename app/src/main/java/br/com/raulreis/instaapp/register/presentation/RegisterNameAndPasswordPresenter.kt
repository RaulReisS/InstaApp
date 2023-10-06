package br.com.raulreis.instaapp.register.presentation


import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.register.RegisterNameAndPassword
import br.com.raulreis.instaapp.register.data.RegisterCallback
import br.com.raulreis.instaapp.register.data.RegisterRepository

class RegisterNameAndPasswordPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
    ) : RegisterNameAndPassword.Presenter {

    override fun onDestroy() {
        view = null
    }

    override fun create(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length >= 3
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirm

        if (!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        }
        else {
            view?.displayNameFailure(null)
        }

        if (!isConfirmValid) {
            view?.displayPasswordFailure(R.string.password_not_equal)
        }
        else {
            view?.displayPasswordFailure(null)
            if (!isPasswordValid) {
                view?.displayPasswordFailure(R.string.invalid_password)
            }
            else {
                view?.displayPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswordValid && isConfirmValid) {
            view?.showProgress(true)

            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }
}