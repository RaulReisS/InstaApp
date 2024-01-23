package br.com.raulreis.instaapp.search.data

import android.os.Handler
import android.os.Looper
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Database
import br.com.raulreis.instaapp.common.model.User

class SearchFakeRemoteDataSource: SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val users = Database.usersAuth.filter {
                it.name.lowercase().startsWith(name.lowercase()) && it.uuid != Database.sessionAuth!!.uuid
            }

            // callback.onSuccess(users.toList())

            callback.onComplete()

        }, 2000)
    }
}