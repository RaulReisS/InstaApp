package br.com.raulreis.instaapp.post

import android.net.Uri
import br.com.raulreis.instaapp.common.base.BasePresenter
import br.com.raulreis.instaapp.common.base.BaseView
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth

interface Post {

    interface Presenter : BasePresenter {
        fun selectUri(uri: Uri)
        fun getSelectedUri(): Uri?
        fun fetchPictures()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullPictures(pictures: List<Uri>)
        fun displayEmptyPictures()
        fun displayRequestFailure(message: String)

    }
}