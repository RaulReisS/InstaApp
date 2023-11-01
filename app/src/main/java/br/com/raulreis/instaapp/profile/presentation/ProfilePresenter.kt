package br.com.raulreis.instaapp.profile.presentation


import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth
import br.com.raulreis.instaapp.profile.Profile
import br.com.raulreis.instaapp.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
    ) : Profile.Presenter {

    override fun clear() {
        repository.clearCache()
    }

    override fun fetchUserProfile() {
        view?.showProgress(true)
        repository.fetchUserProfile(object : RequestCallback<UserAuth> {
            override fun onSuccess(data: UserAuth) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {

            }
        })
    }

    override fun fetchUserPosts() {
        repository.fetchUserPosts(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPosts()
                }
                else {
                    view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}