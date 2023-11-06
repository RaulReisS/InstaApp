package br.com.raulreis.instaapp.post.data

import android.net.Uri

interface PostDataSource {
    suspend fun fetchPictures() : List<Uri>
}