package br.com.raulreis.instaapp.add.data

import android.net.Uri
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.UserAuth

interface AddDataSource {

    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        throw UnsupportedOperationException()
    }

    fun fetchSession() : UserAuth { throw UnsupportedOperationException() }
}