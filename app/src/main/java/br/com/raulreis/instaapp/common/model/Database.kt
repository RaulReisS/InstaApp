package br.com.raulreis.instaapp.common.model

import java.util.UUID

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userA@gmail.com", "12456789"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB@gmail.com", "98765421"))
    }
}