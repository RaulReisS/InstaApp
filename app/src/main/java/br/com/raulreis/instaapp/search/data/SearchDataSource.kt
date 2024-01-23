package br.com.raulreis.instaapp.search.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.User

interface SearchDataSource {
    fun fetchUsers(name: String, callback: RequestCallback<List<User>>)
}