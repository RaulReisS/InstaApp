package br.com.raulreis.instaapp.register.presentation


import android.net.Uri
import br.com.raulreis.instaapp.register.RegisterPhoto
import br.com.raulreis.instaapp.register.data.RegisterCallback
import br.com.raulreis.instaapp.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhoto.View?,
    private val repository: RegisterRepository
    ) : RegisterPhoto.Presenter {

    override fun onDestroy() {
        view = null
    }

    override fun updateUser(photoUri: Uri) {
            view?.showProgress(true)

            repository.updateUser(photoUri, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onUpdateSuccess()
                }

                override fun onFailure(message: String) {
                    view?.onUpdateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
    }
}