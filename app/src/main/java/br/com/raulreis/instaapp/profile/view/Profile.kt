package br.com.raulreis.instaapp.profile.view

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile()

        fun fetchUserPosts()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: UserAuth)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }

}