package com.zs.mol.model.server

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.zs.mol.model.GameManager
import com.zs.mol.model.server.GameServer.CollectionName.USERS
import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserManager

object GameServer {
    val TAG = "Server"

    object CollectionName {
        const val USERS = "users"
    }

    fun createId() {
        val db = FirebaseFirestore.getInstance()
        db.collection(USERS)
            .add("")
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                // TODO init game 해줘야 함...
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }

    fun save(user: User) {
        val db = FirebaseFirestore.getInstance()

        db.collection(USERS)
            .document(user.id)
            .set(user.toSaveData())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun load(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection(USERS)
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                Log.d(TAG, "${document.id} => ${document.data}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}