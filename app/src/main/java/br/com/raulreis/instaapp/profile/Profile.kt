package br.com.raulreis.instaapp.profile

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile(uuid: String?)

        fun fetchUserPosts(uuid: String?)

        fun followUser(uuid: String?, follow: Boolean)

        fun clear()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<UserAuth, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }

}