package br.com.raulreis.instaapp.common.model

import java.util.UUID

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(),"UserA", "userA@gmail.com", "12456789"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(),"UserB", "userB@gmail.com", "98765421"))
    }
}