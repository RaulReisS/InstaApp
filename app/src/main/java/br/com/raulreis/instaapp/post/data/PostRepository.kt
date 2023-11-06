package br.com.raulreis.instaapp.post.data

import android.net.Uri

class PostRepository(private val dataSource: PostDataSource) {

    suspend fun fetchPictures(): List<Uri> {
        return dataSource.fetchPictures()
    }
}