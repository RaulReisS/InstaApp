package br.com.raulreis.instaapp.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, Set<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(UUID.randomUUID().toString(),"UserA", "userA@gmail.com", "12456789", Uri.fromFile(File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-50-33-998.jpg")))
        val userB = UserAuth(UUID.randomUUID().toString(),"UserB", "userB@gmail.com", "98765421", Uri.fromFile(File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-50-33-998.jpg")))
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        feeds[userA.uuid]?.addAll(
            arrayListOf(
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-51-07-513.jpg")),
                    "desc1",
                    System.currentTimeMillis(), userA),

                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-51-07-513.jpg")),
                    "desc2",
                    System.currentTimeMillis(), userA),

                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-51-07-513.jpg")
                ),
                    "desc3",
                    System.currentTimeMillis(), userA),

                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/self/primary/Android/media/br.com.raulreis.instaapp/InstaApp/2023-10-31-23-51-07-513.jpg")),
                    "desc4",
                    System.currentTimeMillis(), userA),
            )
        )

        feeds[userA.uuid]?.toList()?.let {
            feeds[userB.uuid]?.addAll(it)
        }

        sessionAuth = usersAuth.first()
    }
}