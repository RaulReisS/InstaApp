package br.com.raulreis.instaapp.add.data

import android.net.Uri
import br.com.raulreis.instaapp.common.base.RequestCallback
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.lang.RuntimeException

class FireAddDataSource : AddDataSource {
    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        val uriLasPath = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid img")

        val imgRef = FirebaseStorage.getInstance().reference
            .child("images/")
            .child(userUUID)
            .child(uriLasPath)

        imgRef.putFile(uri)
            .addOnSuccessListener { res ->
                imgRef.downloadUrl
                    .addOnSuccessListener { resDownload ->
                        FirebaseFirestore.getInstance()
                            .collection("/users")
                            .document(userUUID)
                            .get()
                            .addOnSuccessListener { resMe ->
                                val me = resMe.toObject(User::class.java)

                                val postRef = FirebaseFirestore.getInstance()
                                    .collection("/posts")
                                    .document(userUUID)
                                    .collection("posts")
                                    .document()

                                val post = Post(
                                    uuid = postRef.id,
                                    photoUrl = resDownload.toString(),
                                    caption = caption,
                                    timestamp = System.currentTimeMillis(),
                                    publisher = me
                                )

                                postRef.set(post)
                                    .addOnSuccessListener { resPost ->

                                        //Meu Feed
                                        FirebaseFirestore.getInstance()
                                            .collection("/feeds")
                                            .document(userUUID)
                                            .collection("posts")
                                            .document(postRef.id)
                                            .set(post)
                                            .addOnSuccessListener { resMyFeed ->

                                                // Feed dos meus seguidores
                                                FirebaseFirestore.getInstance()
                                                    .collection("/followers")
                                                    .document(userUUID)
                                                    .collection("followers")
                                                    .get()
                                                    .addOnSuccessListener { resFollowers ->

                                                        val documents = resFollowers.documents

                                                        for (document in documents) {
                                                            val followersUUID =
                                                                document.toObject(String::class.java)
                                                                    ?: throw RuntimeException("Falha ao converter seguidor")

                                                            FirebaseFirestore.getInstance()
                                                                .collection("/feed")
                                                                .document(followersUUID)
                                                                .collection("posts")
                                                                .document(postRef.path)
                                                                .set(post)
                                                        }
                                                        callback.onSuccess(true)
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        callback.onFailure(
                                                            exception.message
                                                                ?: "Falha ao buscar meus seguidores"
                                                        )
                                                    }
                                                    .addOnCompleteListener {
                                                        callback.onComplete()
                                                    }
                                            }

                                    }
                                    .addOnFailureListener { exception ->
                                        callback.onFailure(
                                            exception.message ?: "Erro ao inserir publicação"
                                        )
                                    }
                            }
                            .addOnFailureListener { exception ->
                                callback.onFailure(
                                    exception.message ?: "Falha ao buscar usuário logado"
                                )
                            }
                    }
                    .addOnFailureListener { exception ->
                        callback.onFailure(exception.message ?: "Erro ao baixar a imagem")
                    }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao enviar imagem")
            }
    }
}