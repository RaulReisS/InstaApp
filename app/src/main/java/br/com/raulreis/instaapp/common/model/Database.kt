package br.com.raulreis.instaapp.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(UUID.randomUUID().toString(),"UserA", "userA@gmail.com", "12456789", Uri.fromFile(File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-11-06-17-33-11-125.jpg")))
        val userB = UserAuth(UUID.randomUUID().toString(),"UserB", "userB@gmail.com", "98765421", Uri.fromFile(File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-11-06-17-33-11-125.jpg")))
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        for ( i in 0..30) {
            val user = UserAuth(UUID.randomUUID().toString(), "User$i", "user$i@gmail.com", "123456789", null)
            usersAuth.add(user)
        }

        // sessionAuth = usersAuth.first()

        // followers[sessionAuth!!.uuid]?.add(usersAuth[1].uuid)
    }
}