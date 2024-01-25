package br.com.raulreis.instaapp.home.data

import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireHomeDataSource : HomeDataSource {
    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario não encontrado")

        FirebaseFirestore.getInstance()
            .collection("/feeds") // Vários feeds
            .document(uid) // Um usuário (corrente)
            .collection("posts") // posts dos outros usuários
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val feed = mutableListOf<Post>()
                val documents = res.documents

                for(document in documents) {
                    val post = document.toObject(Post::class.java)
                    post?.let { feed.add(it)  }
                }
                callback.onSuccess(feed)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao carregar o feed")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}