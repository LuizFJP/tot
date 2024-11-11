package com.ddm.steps.firebase.readtogether.ui.services

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.util.UUID

class FireStorageService {
    companion object {
        fun uploadToStorage(uri: Uri, context: Context) {
            val storage = Firebase.storage
            var storageRef = storage.reference
            val  unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference

            spaceRef = storageRef.child("images/$unique_image_name.jpg")

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener{
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        context,
                        "upload successes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        fun uploadProfilePhotoToStorage(
            bitmap: Bitmap,
            userId: String,
            onSuccess: (String) -> Unit,
            onFailure: (Exception) -> Unit
        ) {
            // Convert Bitmap to ByteArray
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            // Define the storage path
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_photos/$userId.jpg")

            // Upload the image to Firebase Storage
            val uploadTask = storageRef.putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Retrieve the download URL upon successful upload
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    onSuccess(downloadUrl) // Pass the download URL to onSuccess callback
                }.addOnFailureListener { exception ->
                    onFailure(exception) // Pass exception to onFailure callback
                }
            }.addOnFailureListener { exception ->
                onFailure(exception) // Pass exception to onFailure callback
            }
        }

        fun uploadImageToFirebaseStorage(imageUri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_photos/${System.currentTimeMillis()}.jpg")

            storageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Get the download URL
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        Log.d("FirebaseStorage", "Image uploaded successfully. URL: $downloadUrl")
                        onSuccess(downloadUrl) // Pass the download URL as needed
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }

    }
}
