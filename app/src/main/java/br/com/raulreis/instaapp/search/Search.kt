package br.com.raulreis.instaapp.search

import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView
import br.com.raulreis.instaapp.common.model.User

interface Search {

    interface Presenter : BasePresenter {
        fun fetchUsers(name: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled : Boolean)
        fun displayFullUsers(users: List<User>)
        fun displayEmptyUsers()
    }

}