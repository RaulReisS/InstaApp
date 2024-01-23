package br.com.raulreis.instaapp.register.data

import android.net.Uri
import br.com.raulreis.instaapp.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireRegisterDataSource : RegisterDataSource {
    override fun create(email: String, callback: RegisterCallback) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty)
                    callback.onSuccess()
                else
                    callback.onFailure("Usuário já cadastrado")
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                // Aqui ja tem um usuário criado no Authentication
                val uid = result.user?.uid
                if (uid == null)
                    callback.onFailure("Erro interno no servidor")
                else {
                    FirebaseFirestore.getInstance()
                        .collection("/users")
                        .document(uid)
                        .set(
                            hashMapOf(
                                "name" to name,
                                "email" to email,
                                "followers" to 0,
                                "following" to 0,
                                "postCount" to 0,
                                "uuid" to uid,
                                "photoUrl" to null
                            )
                        )
                        .addOnSuccessListener {
                            callback.onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            callback.onFailure(exception.message ?: "Erro interno no servidor")
                        }
                        .addOnCompleteListener {
                            callback.onComplete()
                        }
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null || photoUri.lastPathSegment == null)  {
            callback.onFailure("Usuário não encontrado")
            return
        }
        val storageRef = FirebaseStorage.getInstance().reference

        val imgRef =storageRef.child("images/")
            .child(uid)
            .child(photoUri.lastPathSegment!!)

        imgRef.putFile(photoUri)
            .addOnSuccessListener { result ->
                // Aqui a foto já subiu para o Fire Storage

                imgRef.downloadUrl
                    .addOnSuccessListener{res ->
                        val usersRef = FirebaseFirestore.getInstance().collection("/users").document(uid)

                        usersRef.get()
                            .addOnSuccessListener { document ->
                               val user = document.toObject(User::class.java)
                                val newUser = user?.copy(photoUrl = res.toString())

                                if (newUser != null)
                                    usersRef.set(newUser)
                                        .addOnSuccessListener {
                                            callback.onSuccess()
                                        }
                                        .addOnFailureListener { exception ->
                                            callback.onFailure(exception.message ?: "Falha ao atualizar a foto")
                                        }
                                        .addOnCompleteListener {
                                            callback.onComplete()
                                        }
                            }
                    }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao subir a foto")
            }
    }
}