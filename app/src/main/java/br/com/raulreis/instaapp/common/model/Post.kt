package br.com.raulreis.instaapp.common.model

data class Post(
    val uuid: String? = null,
    val photoUrl: String? = null,
    val caption: String? = null,
    val timestamp: Long? = null,
    val publisher: User? = null
)
