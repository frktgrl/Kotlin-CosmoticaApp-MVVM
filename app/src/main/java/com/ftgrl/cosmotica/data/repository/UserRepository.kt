package com.ftgrl.cosmotica.data.repository

import com.ftgrl.cosmotica.data.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signUpUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser?.uid ?: ""

                    val user = User(
                        id = userId,
                        name = "Username",
                        address = "Example Address",
                        telephone = "123456789",
                        email = email,
                        favorite = emptyList()
                    )

                    firestore.collection("users")
                        .document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onFailure(e)
                        }
                } else {
                    task.exception?.let {
                        onFailure(it)
                    }
                }
            }
    }


    fun addFavoriteProduct(productId: Long) {

        val userId = auth.currentUser?.uid ?: return
        val userRef = firestore.collection("users").document(userId)

        firestore.runTransaction { transaction ->
            val userSnapshot = transaction.get(userRef)
            val user = userSnapshot.toObject(User::class.java) ?: return@runTransaction

            // Eğer ürün zaten favorilerde yoksa ekleyin
            if (!user.favorite.contains(productId.toInt())) {
                val updatedFavorites =
                    user.favorite.toMutableList().apply { add(productId.toInt()) }
                transaction.update(userRef, "favorite", updatedFavorites)
            }

        }
    }
}
