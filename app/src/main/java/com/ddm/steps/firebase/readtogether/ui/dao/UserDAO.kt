package com.ddm.steps.firebase.readtogether.ui.dao

import android.content.ContentValues
import android.util.Log
import com.ddm.steps.firebase.readtogether.ui.states.FriendData
import com.ddm.steps.firebase.readtogether.ui.states.UserUiState
import com.ddm.steps.firebase.readtogether.ui.utils.generateCode
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class UserDAO {

    private val db = Firebase.firestore

    fun create(uid: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fcmToken = task.result
                createUser(uid, fcmToken)
            } else {
                println("Error fetching FCM token: ${task.exception}")
                createUser(uid, null)
            }
        }


    }

    fun createUser(uid: String, token: String?) {
        val userData = mapOf(
            "uid" to uid,
            "username" to "",
            "referral_code" to generateCode(),
            "friends" to ArrayList<FriendData>(),
            "profile_photo_url" to "gs://toth-3cda6.appspot.com/profile_picture.png",
            "friends_invite" to ArrayList<FriendData>(),
            "fmc_token" to token
        )

        db.collection("users")
            .add(userData)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    suspend fun addFriendInvite(referralCode: String) {
        val friendSender = FriendData()
        val user = Firebase.auth.currentUser
        user?.let {
            val thisUser = get(it.uid)
            if (thisUser != null) {
                friendSender.referralCode = thisUser.referralCode
                friendSender.username = thisUser.username
                friendSender.profilePhotoUrl = thisUser.profilePhotoUrl
            }
        }

        val querySnapshot = db.collection("users")
            .whereEqualTo("referral_code", referralCode)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents[0]
            val userRef = userDocument.reference
            try {
                userRef.update("friends_invite", FieldValue.arrayUnion(friendSender)).await()
                println("Friend invite added successfully.")
            } catch (e: Exception) {
                println("Error adding friend invite: ${e.message}")
            }
        } else {
            println("No user found with the referral code: $referralCode")
        }
    }


suspend fun getUserByReferralCode(referralCode: String): UserUiState? {
    val userRef = db.collection("users")
        .whereEqualTo("referral_code", referralCode)
        .get().await()

    return try {
        userRef
            .documents
            .firstOrNull()?.toObject<UserUiState>()
    } catch (e: Exception) {
        println("Error getting user: ${e.message}")
        null
    }
}

suspend fun get(uid: String): UserUiState? {
    val userRef = db.collection("users").whereEqualTo("uid", uid)
        .get()
        .await()

    return try {
        userRef.documents.firstOrNull()?.toObject<UserUiState>()
    } catch (e: Exception) {
        println("Error getting user: ${e.message}")
        null
    }
}

fun updateProfileUrl(uid: String, profilePhotoUrl: String) {
    updateField(uid, "profilePhotoUrl", profilePhotoUrl)
}

fun updateUsername(uid: String, username: String) {
    updateField(uid, "username", username)
}

fun updateField(uid: String, fieldProperty: String, field: Any) {
    val userRef = db.collection("users").document(uid)
    userRef.get().addOnSuccessListener {
        userRef.set(
            mapOf(fieldProperty to field),
            SetOptions.merge()
        ).addOnSuccessListener {
            println("$field updated successfully")
        }.addOnFailureListener { e ->
            println("Error updating $field: ${e.message}")
        }
    }.addOnFailureListener { e ->
        println("Error fetching user document: ${e.message}")
    }
}

fun updateUsername(uid: String) {

}
}

